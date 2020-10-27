package com.example.demo.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//
@RestController
@RequestMapping("management/api/v1/students") //http://localhost:8080/management/api/v1/students/.... adresine req edildiğinde class içi çalışır
public class StudentManagementController {
	
	///http://localhost:8080/api/v1/students/.... adresine req edildiğinde class içi çalıştırılıyor
	private static final List<StudentModel> STUDENTSLİST = Arrays.asList( //demo için bir  student list oluşturduk
		new StudentModel(1,"James Bond"),
		new StudentModel(2, "Maria Jones"),
		new StudentModel(3, "Anna Smith")
	);
	
	@GetMapping //http://localhost:8080/api/v1/students adresine GET req yapılınca çalışır
	public List<StudentModel> getAllStudents(){
		System.out.println("getAllStudents");
		return STUDENTSLİST;
	}
	
	@PostMapping //http://localhost:8080/api/v1/students adresine POST req yapılınca çalışır
	public void registerNewStudent(@RequestBody StudentModel studentModel) { //@RequestBody ile client tarafından formda doldurulan data, studentmodel objesi olarak alınır
		System.out.println("registerNewStudent");
		System.out.println(studentModel);
	}
	
	@DeleteMapping(path = "{studentId}") //http://localhost:8080/api/v1/students/221 adresine DELETE req yapılınca çalışır
	public void deleteStudent(@PathVariable("studentId") Integer studentId) {
		System.out.println("deleteStudent");
		System.out.println(studentId);
	}
	
	@PutMapping(path = "{studentId}") //http://localhost:8080/api/v1/students/221 adresine PUT req yapılınca çalışır
	public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody StudentModel student) {
		System.out.println("updateStudent");
		System.out.println(String.format("%s, %s", studentId, student));
	}

}
