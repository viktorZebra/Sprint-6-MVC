package com.springwebmvc.webProject.repository

import com.springwebmvc.webProject.model.AddressBookModel
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

class AddressBookRepository(private val addressBook: ConcurrentHashMap<Int, AddressBookModel>){

    fun save(addressBookModel: AddressBookModel){
        addressBook[addressBook.size] = addressBookModel
    }

    fun getAll(): ConcurrentHashMap<Int, AddressBookModel>{
        if (addressBook.isEmpty())
            return ConcurrentHashMap()

        return addressBook
    }

    fun getAddressParams(allParams: Map<String, String>): ConcurrentHashMap<Int, AddressBookModel>{
        // как то проще не пришло в голову как тут можно сделать
        // в итоге решил что просто буду выполнять поиск по каждому параметру отдельно
        // сначала по имени если оно не неопределенно, потом по фамилии и тд, таким образом поиск сузится до нужных результатов

        val resultSearch = ConcurrentHashMap<Int, AddressBookModel>()

        val id = allParams.getOrDefault("id", "undefined")
        val name = allParams.getOrDefault("name", "undefined")
        val surname = allParams.getOrDefault("surname", "undefined")
        val address = allParams.getOrDefault("address", "undefined")
        val telephone = allParams.getOrDefault("telephone", "undefined")

        for ((k, v) in addressBook){
            if (id != "undefined"){
                if (k != id.toInt())
                    continue
            }

            if (name != "undefined"){
                if (v.name != name)
                    continue
            }

            if (surname != "undefined"){
                if (v.surname != surname)
                    continue
            }

            if (address != "undefined"){
                if (v.address != address)
                    continue
            }

            if (telephone != "undefined"){
                if (v.telephone != telephone)
                    continue
            }

            resultSearch[k] = v
        }

        return resultSearch
    }

    fun edit(id: Int, addressBookNew: AddressBookModel): AddressBookModel? {
        if (addressBookNew.name.isNotEmpty())
            addressBook[id]!!.name = addressBookNew.name

        if (addressBookNew.surname.isNotEmpty())
            addressBook[id]!!.surname = addressBookNew.surname

        if (addressBookNew.address.isNotEmpty())
            addressBook[id]!!.address = addressBookNew.address

        if (addressBookNew.telephone.isNotEmpty())
            addressBook[id]!!.telephone = addressBookNew.telephone

        return addressBook[id]
    }

    fun delete(id: Int): AddressBookModel? {
        val tmp = addressBook[id]
        addressBook.remove(id)

        return tmp
    }
}