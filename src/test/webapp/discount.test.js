import test from 'ava';

import {Discount} from '../../main/webapp/discount.js'

test('Test Discount constructor', t => {
    const d = new Discount(["MONDAY", 'WEDNESDAY'], 10, 15);
    t.true(d.days.includes('MONDAY'));
    t.true(d.days.includes('WEDNESDAY'));
    t.is(d.price, 10);
    t.is(d.amount, 15);
})