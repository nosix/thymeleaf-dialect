package org.musyozoku.thymeleaf.dialect

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.io.PrintWriter

@RunWith(SpringRunner::class)
@SpringBootTest
class ApplicationTests {

    @Autowired
    lateinit var templateEngine: TemplateEngine

	@Test
	fun contextLoads() {
        Context().run {
            templateEngine.process("test001", this, PrintWriter(System.out))
        }
	}
}
