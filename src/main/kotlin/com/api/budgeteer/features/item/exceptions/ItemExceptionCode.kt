package com.api.budgeteer.features.item.exceptions


enum class ItemExceptionCode(val code: Int) {
    ITEM_NOT_CREATED(1000),
    ITEM_NOT_FOUND(1001),
    ITEM_APPROVAL_NOT_CREATED(1002),
    ITEM_APPROVAL_NOT_FOUND(1003)
}