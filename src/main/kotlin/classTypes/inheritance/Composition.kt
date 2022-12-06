package classTypes.inheritance

class Composition {
    //an object of an existing class is placed in another class to reuse its functionality, not its form
    //composition is a "has-a" relationship

    interface Building
    interface Kitchen
    interface Bathroom

    interface House: Building{ //this is inheritance: a house is-a building
        val kitchen: Kitchen //this is composition: a house has-a kitchen
        val bathrooms: List<Bathroom>
    }
}