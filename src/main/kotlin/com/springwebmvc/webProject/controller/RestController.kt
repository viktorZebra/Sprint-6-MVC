package com.springwebmvc.webProject.controller

import com.springwebmvc.webProject.model.AddressBookModel
import com.springwebmvc.webProject.service.AddressBookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ConcurrentHashMap

@RestController
@RequestMapping("/api")
class RestController @Autowired constructor(val addressBookService: AddressBookService) {

    @PostMapping("/add")
    fun addAddress(@RequestBody addressBook: AddressBookModel): ResponseEntity<AddressBookModel> {
        addressBookService.addNewEntry(addressBook)

        return ResponseEntity(addressBook, HttpStatus.OK)
    }

    @GetMapping("/list")
    fun getAddress(@RequestBody allRequestParams: Map<String, String>): ResponseEntity<ConcurrentHashMap<Int, AddressBookModel>> {
        val searchResult = addressBookService.getAddressWithParams(allRequestParams)

        return ResponseEntity(searchResult, HttpStatus.OK)
    }

    @GetMapping("/{id}/view")
    fun getAddressByID(@PathVariable id: String): ResponseEntity<ConcurrentHashMap<Int, AddressBookModel>> {
        val parameter = mapOf("id" to id)
        val searchResult = addressBookService.getAddressWithParams(parameter)

        return ResponseEntity(searchResult, HttpStatus.OK)
    }

    @PutMapping("/{id}/edit")
    fun editAddress(
        @PathVariable id: String,
        @RequestBody addressBook: AddressBookModel
    ): ResponseEntity<AddressBookModel> {
        val tmp = addressBookService.editAddress(id.toInt(), addressBook)

        return ResponseEntity(tmp, HttpStatus.OK)
    }

    @DeleteMapping("/{id}/delete")
    fun deleteAddress(@PathVariable id: String): ResponseEntity<AddressBookModel> {
        return ResponseEntity(addressBookService.deleteAddress(id.toInt()), HttpStatus.OK)
    }
}