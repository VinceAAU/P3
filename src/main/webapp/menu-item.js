import {Discount} from "./discount.js";

export {MenuItem}

class MenuItem {
    internalName = "";
    displayName = "";
    basePrice = 0;
    minOptions = 0;
    maxOptions = -1;
    options = [];
    additions = [];
    discount = new Discount();
}