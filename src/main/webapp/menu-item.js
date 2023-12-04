import {Discount} from "./discount.js";
import {Option} from "./option.js"

export {MenuItem}

/**
 * @property {Option[]} options
 */
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