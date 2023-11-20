let arrayOpt = []
let arrayAdd = []
let menuItem = []
let addArrCounter = 0
let optArrCounter = 0
let menuCounter = 0


function addOption()
{
    let name =document.getElementById('OptName')
    let price = document.getElementById('OptPrice')
    let allergy= [document.getElementById("vegan").value,document.getElementById("vegeterian").value, document.getElementById("glutenFree").value,document.getElementById("lactose").value,document.getElementById("fingerfood").value]
//I clearly use hannah montage linux because I fear no god with my methods of doing that line
    let description =document.getElementById("OptDesc")

    arrayOpt[optArrCounter] = {name:name.value,price:price.value,description:description.value,allergy:allergy}
    optArrCounter = arrayOpt.length
    writeTextArea("OptText",arrayOpt)
    name.value = null
    price.value = null
    description.value = null

}

function addAddtions()
{
    let name = document.getElementById("addName")
    let price = document.getElementById("addPrice")
    let description =document.getElementById("addDesc")
    arrayAdd[addArrCounter] = {name:name.value,price:price.value,description:description.value}
    addArrCounter = arrayAdd.length
    writeTextArea("AddText",arrayAdd)
    name.value = null
    price.value = null
    description.value = null
}


const addOptToListButton = document.getElementById("AddOPtToListBut")
const addAddToListButton =document.getElementById("AddAddTolistBut")
addOptToListButton.addEventListener('click', (event) => {
    addOption()
})

addAddToListButton.addEventListener('click',(event) => {
    addAddtions()
})


// next 4 functs are 2 eventlisterens on check boxed which hides/reveals their repsected element on marked or unmarked
const optCheck = document.getElementById('RevelOpt')
optCheck.addEventListener('change', (event) => {
    if (event.currentTarget.checked) {
        revealOpt(false)
    }
    else
    {
        revealOpt(true)
    }
})

const addCheck = document.getElementById('AddBox')
addCheck.addEventListener('change', (event) => {
    if (event.currentTarget.checked) {
        revealAdd(false)
    }
    else
    {
        revealAdd(true)
    }
})
function revealOpt(input)
{
    let opt = document.getElementById("opt")
    opt.hidden = input
}

function revealAdd(input)
{
 let add = document.getElementById("AddDiv")
    add.hidden = input
}
function clearinfo()
{
    document.getElementById("DisplayName").value = null
    document.getElementById("InternalName").value = null
    document.getElementById("OrignalPrice").value = null
    document.getElementById("opt").hidden = true
    document.getElementById("RevelOpt").checked = false
    document.getElementById("AddDiv").hidden = true
    document.getElementById("AddBox").checked = false
    document.getElementById("AddText").value = null
    document.getElementById("OptText").value = null
    arrayOpt = []
    arrayAdd = []
    addArrCounter = 0
    optArrCounter = 0

}
document.getElementById("Done").addEventListener("click",(event)=>{
    SendMenuToServer()
})
document.getElementById("SubmitBut").addEventListener("click",(event)=>{
ContructMenu()
})
function ContructMenu()
{
    menuItem[menuCounter] =
        {
            name:document.getElementById("InternalName").value,
            DisplayName:document.getElementById("DisplayName").value, //not needed?
            basePrice:document.getElementById("OrignalPrice").value,
            minOptions:0,
            maxOptions:0,
            options:arrayOpt,
            additions:arrayAdd
        }

    menuCounter = menuItem.length
    clearinfo()
}
document.getElementById("ImportMenuBotton").addEventListener('click',(event => {ImportMenu()}))
function ImportMenu()
{
  //get menu from server
    let thing
  menuItem = thing.items

    document.getElementById("MenuId").value =thing.menuId

    document.getElementById("StartTime").value = thing.availableTimes.start

    document.getElementById("Apocalypse").value = thing.availableTimes.end

}

function GoToItem(Number)
{
    menuCounter = Number
    arrayAdd = menuItem[Number].additions
    arrayOpt = menuItem[Number].options
    document.getElementById("DisplayName").value = menuItem[Number].name
    document.getElementById("OrignalPrice").value = menuItem[Number].basePrice
    writeTextArea("OptText",arrayOpt)
    writeTextArea("AddText",arrayAdd)

}


function SendMenuToServer()
{

    let dayArray=[]

    if (document.getElementById("moooooooon-day").value)
    {
        dayArray.push("Monday");
    }
    if (document.getElementById("tuesday").value)
    {
        dayArray.push("Tuesday");
    }
    if (document.getElementById("wednesday").value)
    {
        dayArray.push("Wednesday")
    }
    if (document.getElementById("thursday").value)
    {
        dayArray.push("Thursday")
    }
    if (document.getElementById("friday").value)
    {
        dayArray.push("Friday")
    }
    if (document.getElementById("saturday").value)
    {
        dayArray.push("Saturday")
    }
    if (document.getElementById("sunday").value)
    {
        dayArray.push("Sunday")
    }

    let json = {
    menu: {
        menuId: document.getElementById("MenuId"),
            availableTimes: {
            start: document.getElementById("StartTime"),
                end: document.getElementById("Apocalypse"),
                days:dayArray
        },
        items:menuItem
        ,
            discount: {
            days: ["Monday", "Tuesday"],
                price: -1000
        }
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
        array[i].name
        text.value += array[i].name + ": " + array[i].price + "\n"
    }
}

//When you click a button the respected function is called
document.getElementById("SetItemButton").addEventListener('click',(event)=>{GoToItem(document.getElementById("SetItemForm").value)})
//unlike these buttons who change a gobale varible and set value in froms
document.getElementById("GoToOptButton").addEventListener("click",(event)=>{
    optArrCounter = document.getElementById("GoToOptForm").value
    document.getElementById("OptName").value = arrayOpt[optArrCounter].name
    document.getElementById("OptPrice").value = arrayOpt[optArrCounter].price
    document.getElementById("OptDesc").value = arrayOpt[optArrCounter].description


})
document.getElementById("GoToAddButton").addEventListener("click",(event)=>{
    addArrCounter = document.getElementById("GoToAddForm").value
    document.getElementById("addName").value = arrayAdd[addArrCounter].name
    document.getElementById("addPrice").value = arrayAdd[addArrCounter].price
    document.getElementById("addDesc").value = arrayAdd[addArrCounter].description

})
//event listens for buttons that removes a thing of the array
document.getElementById("removeAddArrayButton").addEventListener('click',(event=>
{
    arrayAdd.splice(document.getElementById("removeAddArrayForm").value,1)
}))
document.getElementById("removeOptArrayButton").addEventListener('click',(event=>
{
    arrayOpt.splice(document.getElementById("removeOptArrayForm").value,1)
}))
document.getElementById("removeItemArrayButton").addEventListener('click',(event=>
{
    menuItem.splice(document.getElementById("removeItemArrayForm").value,1)
}))


//todo: add menu's to resturangs.
//todo: ask for json to get a menu

