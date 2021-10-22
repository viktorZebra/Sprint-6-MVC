package com.springwebmvc.webProject

import com.springwebmvc.webProject.controller.MVCController
import com.springwebmvc.webProject.service.AddressBookService
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
@AutoConfigureMockMvc
class WebProjectApplicationTests {
	@MockK
	private lateinit var addressBookService: AddressBookService

	@InjectMocks
	private lateinit var mvcController: MVCController

	init {
	    MockKAnnotations.init(this)
	}

	@Autowired
	private lateinit var mockMvc: MockMvc

	@BeforeEach
	fun before() {
		mockMvc = MockMvcBuilders.standaloneSetup(mvcController).build()
	}


	@Test
	fun getAddNewEntryPageTest() {
		mockMvc.perform(get("/app/add"))
	}

}
