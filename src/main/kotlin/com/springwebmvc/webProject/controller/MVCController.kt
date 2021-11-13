package com.springwebmvc.webProject.controller

import com.springwebmvc.webProject.DAO.AddressBookModel
import com.springwebmvc.webProject.service.AddressBookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/app")
class MVCController  @Autowired constructor(val addressBookService: AddressBookService){

    @GetMapping("/add")
    fun getAddNewEntryPage(): String{
        return "add"
    }

    @PostMapping("/add")
    fun addAddress(@ModelAttribute addressBook: AddressBookModel, model: Model): String {
        addressBookService.addNewEntry(addressBook)
        model.addAttribute("result", "Запись добавлена")

        return "resultOperations"
    }

    @GetMapping("/list")
    fun getAddress(@RequestParam allRequestParams: Map<String, String>, model: Model): String {
        val searchResult = addressBookService.getAddressWithParams(allRequestParams)

        model.addAttribute("result", searchResult)
        model.addAttribute("msg", "Результат поиска:")

        return "list"
    }

    @GetMapping("/{id}/view")
    fun getAddressByID(@PathVariable id: String, model: Model): String {
        val parameter = mapOf("id" to id)
        val searchResult = addressBookService.getAddressWithParams(parameter)
        model.addAttribute("result", searchResult)

        return "view"
    }

    @GetMapping("/{id}/edit")
    fun editAddressPage(@PathVariable id: String): String{
        return "edit"
    }

    @PostMapping("/{id}/edit")
    fun editAddress(@PathVariable id: String, @ModelAttribute addressBook: AddressBookModel, model: Model): String {
        addressBookService.editAddress(id.toInt(), addressBook)
        model.addAttribute("result", "Запись успешно изменена")

        return "resultOperations"
    }

    @GetMapping("/{id}/delete")
    fun deleteAddress(@PathVariable id: String, model: Model): String {
        addressBookService.deleteAddress(id.toInt())
        model.addAttribute("result", "Запись успешно удалена")

        return "resultOperations"
    }
}
