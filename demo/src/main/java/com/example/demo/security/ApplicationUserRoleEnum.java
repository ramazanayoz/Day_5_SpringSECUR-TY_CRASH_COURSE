package com.example.demo.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

import static com.example.demo.security.ApplicationUserPermissionEnum.*;

//ROLES & PERMISSIONS USING ENUMS
public enum ApplicationUserRoleEnum {
	
	//ENUM OBJECTS CREATED //OLUŞTURULAN ENUM OBJELER
	STUDENT(Sets.newHashSet()),
	ADMIN(Sets.newHashSet( COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE  )),
	ADMINTRAINEE(Sets.newHashSet( COURSE_READ, STUDENT_READ ));

	
	//ENUM VERİABLES
	private final Set<ApplicationUserPermissionEnum> permissionsSetArr;
	
	//ENUM CONST
	ApplicationUserRoleEnum(Set<ApplicationUserPermissionEnum> permissionsEnum){
		this.permissionsSetArr = permissionsEnum;
	}
	
	//ENUM METHODS
	public Set<ApplicationUserPermissionEnum> getPermissions() {
		return permissionsSetArr;
	}
	
	//METHOD
	public Set<SimpleGrantedAuthority> getGrantedAuthoritiesSet(){
		//tip dönüşümü yaptık --->  Set<ApplicationUserPermissionEnum>  converting to Set<GrantedAuthority> 
		Set<SimpleGrantedAuthority> permissionsSet = getPermissions().stream()
				.map(permissionEnumObj -> new SimpleGrantedAuthority(permissionEnumObj.getPermission()))
				.collect(Collectors.toSet());
		permissionsSet.add(new SimpleGrantedAuthority("ROLE_"+ this.name()));
		return permissionsSet;
	}
}
