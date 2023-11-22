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


    let labels= []

    if (document.getElementById("vegan").checked)
    {
        labels.push("VEGAN")
    }
    if (document.getElementById("vegeterian").checked)
    {
        labels.push("VEGETARIAN")}
    if (document.getElementById("glutenFree").checked)
    {
        labels.push("GLUTEN_FREE")}
    if (document.getElementById("lactose").checked)
    {
        labels.push("LACTOSE_FREE")}
    if (document.getElementById("fingerfood").checked)
    {
        labels.push("FINGER_FOOD")}

    let displayName =document.getElementById("OptDesc")

    arrayOpt[optArrCounter] = {internalName:name.value,price:price.value,displayName:displayName.value,labels:labels}
    optArrCounter = arrayOpt.length
    writeTextArea("OptText",arrayOpt)
    name.value = null
    price.value = null
    displayName.value = null

}

function addAddtions()
{
    let name = document.getElementById("addName")
    let price = document.getElementById("addPrice")
    let displayName =document.getElementById("addDesc")
    arrayAdd[addArrCounter] = {internalName:name.value,price:price.value,displayName:displayName.value}
    addArrCounter = arrayAdd.length
    writeTextArea("AddText",arrayAdd)
    name.value = null
    price.value = null
    displayName.value = null
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
    document.getElementById("maxOpt").value = null
    document.getElementById("minOpt").value = null
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
    let discountArray = []
    if (document.getElementById("discMonday"))
    {discountArray.push("MONDAY")}
    if (document.getElementById("discTuesday"))
    {discountArray.push("TUESDAY")}
    if (document.getElementById("discWednesday"))
    {discountArray.push("WEDNESDAY")}
    if (document.getElementById("discThursday"))
    {discountArray.push("THURSDAY")}
    if (document.getElementById("discFriday"))
    {discountArray.push("FRIDAY")}
    if (document.getElementById("discSaturday"))
    {discountArray.push("SATURDAY")}
    if (document.getElementById("discSunday"))
    {discountArray.push("SUNDAY")}


    menuItem[menuCounter] =
        {
            internalName:document.getElementById("InternalName").value,
            displayName:document.getElementById("DisplayName").value, //not needed?
            basePrice:document.getElementById("OrignalPrice").value,
            minOptions: document.getElementById("minOpt").value,
            maxOptions:document.getElementById("maxOpt").value,
            options:arrayOpt,
            additions:arrayAdd,
            discount:{
                days:discountArray,
                price:document.getElementById("discountPrice").value,
                amount:document.getElementById("discountAmount").value
            }
        }

    menuCounter = menuItem.length
    clearinfo()
}
document.getElementById("ImportMenuBotton").addEventListener('click',(event => {ImportMenu()}))
function ImportMenu()
{
  //get menu from server
    let givenJson
    const originUrl = 'http://localhost:8080/P3_war//AktuelMenu/MenuGet'

    fetch(originUrl,{
        method: 'POST',
        headers: {
            'Access-Control-Allow-Origin':'http://localhost:8080',
            'Access-Control-Allow-Methods':'POST',
            'Access-Control-Allow-Credentials':'true',
            'Content-Type':'application/json',
            'Accept':'application/json'
        },body:document.getElementById("ImportMenuForm").value
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log('Success:', data);
            menuItem = data.items

            document.getElementById("MenuId").value =data.menuId

            document.getElementById("StartTime").value = data.availableTimes.start

            document.getElementById("Apocalypse").value = data.availableTimes.end

        })
        .catch((error) => {
            console.error('Error:', error);
        });




  //menuItem = givenJson.items


}

function GoToItem(Number)
{
    menuCounter = Number
    arrayAdd = menuItem[Number].additions
    arrayOpt = menuItem[Number].options
    document.getElementById("DisplayName").value = menuItem[Number].displayName
    document.getElementById("InternalName").value =menuItem[Number].internalName
    document.getElementById("OrignalPrice").value = menuItem[Number].basePrice
    writeTextArea("OptText",arrayOpt)
    writeTextArea("AddText",arrayAdd)

}


function SendMenuToServer()
{

    let dayArray=[]

    if (document.getElementById("moooooooon-day").checked)
    {
        dayArray.push("Monday");
    }
    if (document.getElementById("tuesday").checked)
    {
        dayArray.push("Tuesday");
    }
    if (document.getElementById("wednesday").checked)
    {
        dayArray.push("Wednesday")
    }
    if (document.getElementById("thursday").checked)
    {
        dayArray.push("Thursday")
    }
    if (document.getElementById("friday").checked)
    {
        dayArray.push("Friday")
    }
    if (document.getElementById("saturday").checked)
    {
        dayArray.push("Saturday")
    }
    if (document.getElementById("sunday").checked)
    {
        dayArray.push("Sunday")
    }

    let json = {
    menu: {
        menuId: document.getElementById("MenuId"),
            availableTimes: {
            start: document.getElementById("StartTime").value,
                end: document.getElementById("Apocalypse").value,
                days:dayArray
        },
        items:menuItem
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
document.getElementById("SetItemButton").addEventListener('click',(event)=>{GoToItem(document.getElementById("SetItemForm").value)})
//unlike these buttons who change a gobale varible and set value in froms
document.getElementById("GoToOptButton").addEventListener("click",(event)=>{
    optArrCounter = document.getElementById("GoToOptForm").value
    document.getElementById("OptName").value = arrayOpt[optArrCounter].internalName
    document.getElementById("OptPrice").value = arrayOpt[optArrCounter].price
    document.getElementById("OptDesc").value = arrayOpt[optArrCounter].displayName


})
document.getElementById("GoToAddButton").addEventListener("click",(event)=>{
    addArrCounter = document.getElementById("GoToAddForm").value
    document.getElementById("addName").value = arrayAdd[addArrCounter].internalName
    document.getElementById("addPrice").value = arrayAdd[addArrCounter].price
    document.getElementById("addDesc").value = arrayAdd[addArrCounter].displayName

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




