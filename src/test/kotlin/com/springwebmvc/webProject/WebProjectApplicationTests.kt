package com.springwebmvc.webProject

import com.springwebmvc.webProject.model.AddressBookModel
import com.springwebmvc.webProject.service.AddressBookService
import org.hamcrest.core.StringContains.containsString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap


@SpringBootTest
@AutoConfigureMockMvc
class WebProjectApplicationTests {
	@BeforeEach
	fun before(){
		var tmp = AddressBookModel("artem", "pupkin", "123", "123")
		service.addNewEntry(tmp)

		tmp = AddressBookModel("diman", "ivanov", "456", "456")
		service.addNewEntry(tmp)

		tmp = AddressBookModel("ilay", "afima", "789", "789")
		service.addNewEntry(tmp)

		tmp = AddressBookModel("max", "kozlov", "012", "012")
		service.addNewEntry(tmp)
	}

	@Autowired
	private lateinit var service: AddressBookService

	@Autowired
	private lateinit var mockMvc: MockMvc

	@Test
	fun getAddNewEntryPageTest(){
		mockMvc.perform(get("/app/add"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk)
			.andExpect(view().name("add"))
	}

	@Test
	fun addAddressTest() {
		val tmp: MultiValueMap<String, String> = LinkedMultiValueMap()
		tmp.add("name", "artem")
		tmp.add("surname", "pupkin")
		tmp.add("address", "123")
		tmp.add("telephone", "123")

		mockMvc.perform(post("/app/add")
			.params(tmp))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk)
			.andExpect(view().name("resultOperations"))
			.andExpect(content().string(containsString("Запись добавлена")))
	}

	@Test
	fun getAddressTest() {
		mockMvc.perform(get("/app/list")
			.param("name", "artem"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk)
			.andExpect(view().name("list"))
	}

	@Test
	fun getAddressByIDTest() {
		mockMvc.perform(get("/app/0/view"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk)
			.andExpect(view().name("view"))
			.andExpect(content().string(containsString("artem")))
	}

	@Test
	fun editAddressPageTest(){
		mockMvc.perform(get("/app/0/edit"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk)
			.andExpect(view().name("edit"))
	}

	@Test
	fun editAddressTest() {
		mockMvc.perform(post("/app/1/edit")
			.param("name", "Dmitry"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk)
			.andExpect(view().name("resultOperations"))
			.andExpect(content().string(containsString("Запись успешно изменена")))
	}

	@Test
	fun deleteAddressTest() {
		mockMvc.perform(get("/app/2/delete"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk)
			.andExpect(view().name("resultOperations"))
			.andExpect(content().string(containsString("Запись успешно удалена")))
	}
}

