export {Discount}

class Discount{
    days = [];
    price = 0;
    amount = 0;

    constructor(days, price, amount) {
        this.days = days;
        this.price = price;
        this.amount = amount;
    }
}