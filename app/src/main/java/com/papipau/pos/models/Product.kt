package com.papipau.pos.models

import java.math.BigDecimal

data class Product(val upc: String = "", val name: String = "", val price: BigDecimal = BigDecimal(0.0))
