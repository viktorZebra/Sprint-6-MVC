package com.springwebmvc.webProject

import com.springwebmvc.webProject.model.AddressBookModel
import com.springwebmvc.webProject.service.AddressBookService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.util.concurrent.ConcurrentHashMap


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestTests {
	private val headers = HttpHeaders()

	@LocalServerPort
	private var port: Int = 0

	@Autowired
	private lateinit var restTemplate: TestRestTemplate

	@Autowired
	private lateinit var service: AddressBookService

	@BeforeEach
	fun before(){
		headers.add("Cookie", logging())

		var tmp = AddressBookModel("artem", "pupkin", "123", "123")
		service.addNewEntry(tmp)

		tmp = AddressBookModel("diman", "ivanov", "456", "456")
		service.addNewEntry(tmp)

		tmp = AddressBookModel("ilay", "afima", "789", "789")
		service.addNewEntry(tmp)

		tmp = AddressBookModel("max", "kozlov", "012", "012")
		service.addNewEntry(tmp)
	}

	private fun url(s: String): String {
		return "http://localhost:${port}/${s}"
	}

	private fun logging(): String? {
		val request: MultiValueMap<String, String> = LinkedMultiValueMap()
		request.set("username", "admin")
		request.set("password", "password")

		val response = restTemplate.postForEntity(url("login"), HttpEntity(request, HttpHeaders()), String::class.java)

		return response!!.headers["Set-Cookie"]!![0]
	}

	@Test
	fun addAddress() {
		val bookModel = AddressBookModel("artem", "pupkin", "123", "123")
		val response = restTemplate.exchange(
			url("api/add"),
			HttpMethod.POST,
			HttpEntity(bookModel, headers),
			AddressBookModel::class.java
		)

		assertEquals(HttpStatus.CREATED, response.statusCode)
		assertNotNull(response.body)
		assertEquals("artem", response.body!!.name)
	}

	@Test
	fun getAddress() {
		val parameter = mapOf("name" to "diman")
		val response = restTemplate.exchange(
			url("api/list"),
			HttpMethod.POST,
			HttpEntity(parameter, headers),
			object : ParameterizedTypeReference<ConcurrentHashMap<Int, AddressBookModel>>() {}
		)

		assertEquals(HttpStatus.OK, response.statusCode)
		assertNotNull(response.body)
		assertEquals("diman", response.body!![1]!!.name)
	}

	@Test
	fun getAddressByID() {
		val response = restTemplate.exchange(
			url("/api/{id}/view"),
			HttpMethod.GET,
			HttpEntity(null, headers),
			object : ParameterizedTypeReference<ConcurrentHashMap<Int, AddressBookModel>>() {},
			0
		)

		assertEquals(HttpStatus.OK, response.statusCode)
		assertNotNull(response.body)
		assertEquals("artem", response.body!![0]!!.name)
	}

	@Test
	fun editAddress() {
		val bookModel = AddressBookModel("dmitry", "", "", "")
		val response = restTemplate.exchange(
			url("/api/{id}/edit"),
			HttpMethod.PUT,
			HttpEntity(bookModel, headers),
			AddressBookModel::class.java,
			1
		)

		assertEquals(HttpStatus.OK, response.statusCode)
		assertNotNull(response.body)
		assertEquals("dmitry", response.body!!.name)
	}

	@Test
	fun deleteAddress() {
		val response = restTemplate.exchange(
			url("/api/{id}/delete"),
			HttpMethod.DELETE,
			HttpEntity(null, headers),
			AddressBookModel::class.java,
			0
		)

		assertEquals(HttpStatus.OK, response.statusCode)
		assertNotNull(response.body)
		assertThat(service.getAddressWithParams(mapOf("name" to "artem")).isEmpty())
	}

}

