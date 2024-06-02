package com.api.budgeteer.features.item


fun toDTO(item: Item): ItemDTO {
    return ItemDTO(
        id = item.id,
        name = item.name,
        price = item.price,
        quantity = item.quantity,
        userId = item.user.id
    )
}
