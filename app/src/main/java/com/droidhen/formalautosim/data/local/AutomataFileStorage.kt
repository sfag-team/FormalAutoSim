package com.droidhen.formalautosim.data.local

import android.content.Context
import com.droidhen.formalautosim.core.entities.machines.FiniteMachine
import com.droidhen.formalautosim.core.entities.machines.Machine
import com.droidhen.formalautosim.core.entities.machines.MachineType
import com.droidhen.formalautosim.core.entities.machines.PushDownMachine
import com.droidhen.formalautosim.core.entities.machines.PushDownTransition
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

/**
 * File-based storage for automata machines using .jff files
 */
class AutomataFileStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val storageDir: File
        get() {
            val dir = File(context.filesDir, "automata")
            if (!dir.exists()) {
                dir.mkdirs()
            }
            return dir
        }

    /**
     * Save a machine to a .jff file
     */
    fun saveMachine(machine: Machine) {
        val fileName = "${sanitizeFileName(machine.name)}.jff"
        val file = File(storageDir, fileName)
        val jffContent = machine.exportToJFF()
        file.writeText(jffContent)
    }

    /**
     * Get all saved machine names
     */
    fun getAllMachineNames(): List<String> {
        return storageDir.listFiles()
            ?.filter { it.extension == "jff" }
            ?.map { it.nameWithoutExtension }
            ?: emptyList()
    }

    /**
     * Load a machine by name from .jff file
     */
    fun getMachineByName(name: String): Machine? {
        val fileName = "${sanitizeFileName(name)}.jff"
        val file = File(storageDir, fileName)

        if (!file.exists()) {
            return null
        }

        val jffContent = file.readText()
        val (states, transitions) = ExternalStorageController().parseJff(jffContent)

        // Determine machine type from JFF content
        val machineType = if (jffContent.contains("<type>fa</type>")) {
            MachineType.Finite
        } else {
            MachineType.Pushdown
        }

        return if (machineType == MachineType.Finite) {
            FiniteMachine(
                name = name,
                version = 1,
                states = states.toMutableList(),
                transitions = transitions.toMutableList(),
                savedInputs = mutableListOf()
            )
        } else {
            PushDownMachine(
                name = name,
                version = 1,
                states = states.toMutableList(),
                transitions = transitions.toMutableList() as MutableList<PushDownTransition>,
                savedInputs = mutableListOf()
            )
        }
    }

    /**
     * Delete a machine file
     */
    fun deleteMachine(name: String): Boolean {
        val fileName = "${sanitizeFileName(name)}.jff"
        val file = File(storageDir, fileName)
        return file.delete()
    }

    /**
     * Sanitize file name to remove invalid characters
     */
    private fun sanitizeFileName(name: String): String {
        return name.replace(Regex("[^a-zA-Z0-9._-]"), "_")
    }
}
