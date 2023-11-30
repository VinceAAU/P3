import test from 'ava';

import {Discount} from '../../main/webapp/discount.js'
import {MenuItem} from '../../main/webapp/menu-item.js'
test('Test Menu constructor', t => {

    let d = new Discount(["MONDAY", 'WEDNESDAY'], 10, 15);

    let addtions = []
    let options = []

    const menuItem = new MenuItem();
    menuItem.discount = d;
    menuItem.internalName = "hejsa";
    menuItem.displayName = "hejsa";
    menuItem.maxOptions = -1;
    menuItem.minOptions = 0;
    menuItem.basePrice = 0;
    menuItem.additions = addtions;
    menuItem.options = options;


    // how the hell shall i write test?
    //skal jeg sende en post request og hvis den siger okay er alt godt?
    //skal jeg se om json virker
    //discount.js tester bare constructor

})