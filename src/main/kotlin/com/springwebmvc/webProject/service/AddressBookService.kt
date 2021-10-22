package com.springwebmvc.webProject.service

import com.springwebmvc.webProject.model.AddressBookModel
import com.springwebmvc.webProject.repository.AddressBookRepository
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class AddressBookService {
    private val addressBookRepository = AddressBookRepository(ConcurrentHashMap())

    fun addNewEntry(addressBook: AddressBookModel) {
        addressBookRepository.save(addressBook)
    }

    fun getAddressWithParams(allRequestParams: Map<String, String>)
    : ConcurrentHashMap<Int, AddressBookModel> {
        if (allRequestParams.isEmpty()){
            return getAllAddress()
        }

        return addressBookRepository.getAddressParams(allRequestParams)
    }

    fun getAllAddress(): ConcurrentHashMap<Int, AddressBookModel>{
        return addressBookRepository.getAll()
    }

    fun editAddress(id: Int, addressBook: AddressBookModel): AddressBookModel? {
        return addressBookRepository.edit(id, addressBook)
    }

    fun deleteAddress(id: Int): AddressBookModel? {
        return addressBookRepository.delete(id)
    }
}