package com.api.budgeteer.core

import java.util.*


abstract class Traceable{

    abstract var createdAt : Date?
    abstract var updatedAt: Date?
    abstract var deletedAt: Date?


}