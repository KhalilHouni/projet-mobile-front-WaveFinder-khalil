package com.example.wavefinder

data class Spot(
    val name: String,
    val imageResId: Int,
    val location: String
)

val fakeSpots = listOf(
    Spot(
        name = "Spot 1",
        imageResId = R.drawable.spot1,
        location = "Hawaii"
    ),
    Spot(
        name = "Spot 2",
        imageResId = R.drawable.spot2,
        location = "California"
    ),
    Spot(
        name = "Spot 3",
        imageResId = R.drawable.spot3,
        location = "Australia"
    ),
    Spot(
        name = "Spot 4",
        imageResId = R.drawable.spot4,
        location = "Indonesia"
    ),
    Spot(
        name = "Spot 5",
        imageResId = R.drawable.spot4,
        location = "Indonesia"
    )
)
