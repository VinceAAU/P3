import {Option} from './option.js';
import {MenuItem} from "./menu-item.js";
import {Discount} from "./discount.js";

let options = [];
let currentOption = 0;
let additions = [];
let currentAddition = 0;
let menuItems = [];
let currentItem = 0;

/**
 * Reads the options that have been selected, adds it to the global options array, and puts it into the text area
 */
function addOption() {
    const option = new Option();

    option.internalName = document.getElementById('OptName').value;
    option.displayName = document.getElementById("OptDesc").value;
    option.price = document.getElementById('OptPrice').value;


    if (document.getElementById("vegan").checked)
        option.labels.push("VEGAN")
    if (document.getElementById("vegetarian").checked)
        option.labels.push("VEGETARIAN")
    if (document.getElementById("glutenFree").checked)
        option.labels.push("GLUTEN_FREE")
    if (document.getElementById("lactose").checked)
        option.labels.push("LACTOSE_FREE")
    if (document.getElementById("fingerfood").checked)
        option.labels.push("FINGER_FOOD")

    //Deselect all label checkboxes
    document.querySelectorAll("#opt input[type=checkbox]").forEach(e => e.checked=false);

    options[currentOption] = option;
    currentOption = options.length;
    writeTextArea("OptText",options)
    document.querySelector("#OptName").value = null //Are we sure this is the right way to reset these?
    document.querySelector("#OptPrice").value = null;
    document.querySelector("#OptDesc").value = null;

}

/**
 * Reads the additions that have been selected, adds it to the global options array, and puts it into the text area
 */
function addAddition() {
    let addition = new Option();
    addition.internalName = document.getElementById("addName").value;
    addition.price = document.getElementById("addPrice").value;
    addition.displayName =document.getElementById("addDesc").value;
    additions[currentAddition] = addition;
    currentAddition = additions.length;
    writeTextArea("AddText",additions)
    document.querySelector("#addName").value = null
    document.querySelector("#addPrice").value = null
    document.querySelector("#addDesc").value = null
}


document.querySelector("#AddOPtToListBut").addEventListener('click', addOption);

document.querySelector("#AddAddTolistBut").addEventListener('click',addAddition);


//FOR THE RECORD, ALL THE BELOW COULD BE DONE WITH SIMPLE CSS!!!!

/**
 * Handles the dynamic showing of the options editor
 */
document.querySelector("#RevelOpt").addEventListener('change', (event) => {
    if (event.currentTarget.checked) {
        revealOpt(false);
    } else {
        revealOpt(true);
    }
});

/**
 * Handles the dynamic showing of the additions editor
 */
document.querySelector("#AddBox").addEventListener('change', (event) => {
    if (event.currentTarget.checked) {
        revealAdd(false);
    } else {
        revealAdd(true);
    }
});
function revealOpt(input)
{
    let opt = document.getElementById("opt");
    opt.hidden = input;
}

function revealAdd(input)
{
 let add = document.getElementById("AddDiv")
    add.hidden = input
}
function clearInfo()
{
    document.getElementById("DisplayName").value = null;
    document.getElementById("InternalName").value = null;
    document.getElementById("OrignalPrice").value = null;
    document.getElementById("opt").hidden = true;
    document.getElementById("RevelOpt").checked = false;
    document.getElementById("AddDiv").hidden = true;
    document.getElementById("AddBox").checked = false;
    document.getElementById("AddText").value = null;
    document.getElementById("OptText").value = null;
    document.getElementById("maxOpt").value = null;
    document.getElementById("minOpt").value = null;
    options = [];
    additions = [];

}
document.getElementById("Done").addEventListener("click",sendMenuToServer);
document.getElementById("SubmitBut").addEventListener("click",constructMenu);
function constructMenu()
{
    let days = [];
    if (document.getElementById("discMonday"))
        days.push("MONDAY");
    if (document.getElementById("discTuesday"))
        days.push("TUESDAY");
    if (document.getElementById("discWednesday"))
        days.push("WEDNESDAY");
    if (document.getElementById("discThursday"))
        days.push("THURSDAY");
    if (document.getElementById("discFriday"))
        days.push("FRIDAY");
    if (document.getElementById("discSaturday"))
        days.push("SATURDAY");
    if (document.getElementById("discSunday"))
        days.push("SUNDAY");

    let item = new MenuItem();

    item.internalName = document.querySelector("#InternalName").value;
    item.displayName = document.querySelector("#DisplayName").value;
    item.basePrice = document.querySelector("#OrignalPrice").value;
    item.minOptions = document.querySelector("#minOpt").value;
    item.maxOptions = document.querySelector("#maxOpt").value;
    item.options = options; //Using global variables like this isn't particularly object-oriented >:(
    item.additions = additions; //Ditto
    item.discount = new Discount(
        days,
        document.querySelector("#discountPrice").value,
        document.querySelector("#discountAmount").value
    );

    menuItems[currentItem] = item;
    currentItem = menuItems.length;
    clearInfo();
}

//Why make a function for this? Why not just put it into a lambda?
document.querySelector("#ImportMenuBotton").addEventListener('click', importMenu);
function importMenu() {
  //get menu from server
    const originUrl = 'http://localhost:8080/P3_war//AktuelMenu/MenuGet';

    fetch(originUrl,{
        method: 'POST',
        headers: {
            'Access-Control-Allow-Origin':'http://localhost:8080',
            'Access-Control-Allow-Methods':'POST',
            'Access-Control-Allow-Credentials':'true',
            'Content-Type':'application/json',
            'Accept':'application/json'
        },
        body: document.getElementById("ImportMenuForm").value
    })
        .then(response => {
            if (!response.ok)
                throw new Error('Network response was not ok' + response.statusText);
            else
                return response.json();
        })
        .then(data => {
            console.log('Success:', data);
            menuItems = data.items

            document.getElementById("MenuId").value =data.menuId

            document.getElementById("StartTime").value = data.availableTimes.start

            document.getElementById("Apocalypse").value = data.availableTimes.end

        })
        .catch((error) => {
            console.error('Error:', error);
        });




  //menuItems = givenJson.items


}

function GoToItem(number)
{
    additions = menuItems[number].additions
    options = menuItems[number].options
    currentItem = number;
    document.getElementById("DisplayName").value = menuItems[number].displayName
    document.getElementById("InternalName").value =menuItems[number].internalName
    document.getElementById("OrignalPrice").value = menuItems[number].basePrice
    writeTextArea("OptText",options)
    writeTextArea("AddText",additions)

}


function sendMenuToServer()
{

    let dayArray=[]

    if (document.getElementById("monday").checked)
        dayArray.push("Monday");
    if (document.getElementById("tuesday").checked)
        dayArray.push("Tuesday");
    if (document.getElementById("wednesday").checked)
        dayArray.push("Wednesday")
    if (document.getElementById("thursday").checked)
        dayArray.push("Thursday")
    if (document.getElementById("friday").checked)
        dayArray.push("Friday")
    if (document.getElementById("saturday").checked)
        dayArray.push("Saturday")
    if (document.getElementById("sunday").checked)
        dayArray.push("Sunday")

    let json = {
    menu: {
        menuId: document.getElementById("MenuId"),
            availableTimes: {
            start: document.getElementById("StartTime").value,
                end: document.getElementById("Apocalypse").value,
                days:dayArray
        },
        items:menuItems
    }


    }

    const sendUrl = 'http://localhost:8080/P3_war/AktuelMenu/MenuSent'

    fetch(sendUrl,{
        method: 'POST',
        headers: {
            'Access-Control-Allow-Origin':'http://localhost:8080',
            'Access-Control-Allow-Methods':'POST',
            'Access-Control-Allow-Credentials':'true',
            'Content-Type':'application/json',
            'Accept':'application/json'
        },
        body: JSON.stringify(json)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });

}

function writeTextArea(textId,array)
{
    let text = document.getElementById(textId)
    text.value = ""
    for(let i=0; array.length > i;i++)
    {
        array[i].internalName
        text.value += array[i].internalName + ": " + array[i].price + "\n"
    }
}

//When you click a button the respected function is called
document.getElementById("SetItemButton").addEventListener('click',()=>{
    GoToItem(document.getElementById("SetItemForm").value)
});
//unlike these buttons who change a gobale varible and set value in froms
document.getElementById("GoToOptButton").addEventListener("click",()=>{
    let selectedOption = document.getElementById("GoToOptForm").value;

    let option = options[selectedOption];

    document.getElementById("OptName").value = option.internalName;
    document.getElementById("OptPrice").value = option.price;
    document.getElementById("OptDesc").value = option.displayName;

    if(option.labels.includes('VEGAN'))
        document.querySelector("#vegan").checked = true;
    if(option.labels.includes('VEGETARIAN'))
        document.querySelector("#vegetarian").checked = true;
    if(option.labels.includes('GLUTEN_FREE'))
        document.querySelector("#glutenFree").checked = true;
    if(option.labels.includes('LACTOSE_FREE'))
        document.querySelector("#lactose").checked = true;
    if(option.labels.includes('FINGER_FOOD'))
        document.querySelector("#fingerfood").checked = true;


    currentOption = selectedOption;


})
document.getElementById("GoToAddButton").addEventListener("click",()=>{
    let selectedAddition = document.getElementById("GoToAddForm").value;
    document.getElementById("addName").value = additions[selectedAddition].internalName;
    document.getElementById("addPrice").value = additions[selectedAddition].price;
    document.getElementById("addDesc").value = additions[selectedAddition].displayName;
    currentAddition = selectedAddition;
})
//event listens for buttons that removes a thing of the array
document.getElementById("removeAddArrayButton").addEventListener('click',(()=>
{
    additions.splice(document.getElementById("removeAddArrayForm").value,1);
    writeTextArea("AddText", additions);
}))
document.getElementById("removeOptArrayButton").addEventListener('click',(()=>
{
    options.splice(document.getElementById("removeOptArrayForm").value,1);
    writeTextArea("OptText", options);
}))
document.getElementById("removeItemArrayButton").addEventListener('click',(()=>
{
    menuItems.splice(document.getElementById("removeItemArrayForm").value,1);
    writeTextArea("ItemText", menuItems);
}))



