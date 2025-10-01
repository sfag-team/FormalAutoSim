package com.droidhen.formalautosim.core.viewModel

import androidx.lifecycle.ViewModel
import com.droidhen.formalautosim.core.entities.machines.Machine
import com.droidhen.formalautosim.data.local.AutomataFileStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AutomataViewModel @Inject constructor(private val storage: AutomataFileStorage) : ViewModel() {

    fun getAllMachinesName(): List<String> = storage.getAllMachineNames()

    fun getMachineByName(name: String): Machine? = storage.getMachineByName(name)

    fun saveMachine(machine: Machine) {
        storage.saveMachine(machine)
    }
}

object CurrentMachine {
    var machine: Machine? = null
}