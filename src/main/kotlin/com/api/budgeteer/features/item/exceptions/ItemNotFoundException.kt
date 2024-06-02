package com.api.budgeteer.features.item.exceptions

class ItemNotFoundException : Exception {
    constructor(id: Long) : super("Item with id $id not found")
    constructor() : super("Item not found")
}