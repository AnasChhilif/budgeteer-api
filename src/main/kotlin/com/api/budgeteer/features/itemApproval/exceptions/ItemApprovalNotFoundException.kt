package com.api.budgeteer.features.itemApproval.exceptions

class ItemApprovalNotFoundException : Exception {
    constructor() : super("Item approval not found")
    constructor(itemId : Long, userId : Long) : super("Item approval not found for item $itemId and user $userId")
}