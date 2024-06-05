package com.api.budgeteer.features.users

fun toDTO(user: User?): UserDTO{

    val (id, firstName, lastName, email) = user ?: User(0, "", "", "")

    return UserDTO(id, firstName, lastName, email)
}

fun toEntity(userDTO: UserDTO): User{
    return User(userDTO.id ?: 0, userDTO.firstName, userDTO.lastName, userDTO.email)
}