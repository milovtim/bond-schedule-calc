### Main interfaces

* `BondInfoService`: provides API for **fetching** bond descriptor
* `BondInfoStorage`: provides API how to **store** bond descriptor
* `Bond` -- main data class with bond properties

            
### MOEX http client
* `InfoStatService` feign interface. It is client of [ISS service](https://www.moex.com/a2193)
* `MoexBondInfoService` implementation of `BondInfoService` based on `InfoStatService`