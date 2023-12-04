//defining a class for the selected order to be sent back to server
class Order_Item {
    constructor(name,price) {
        this.name = name;
        this.selectedOptions = [];
        this.selectedAdditions = [];
        this.options = [];
        this.additions = [];
        this.price = price;
    }
}
    // url example http://website.com/Dyna_menu.html?table=16&restaurant=Budolfi


const URLParameters = new URLSearchParams(window.location.search);
const tableID = URLParameters.get('table');
const restaurant = URLParameters.get('restaurant');
const menuClassesURL = `menu.json?restaurant=${encodeURIComponent(restaurant)}`; // URL for getting menu from server//url for getting menu from server
const sendURL = `/P3_war/OrderSent?table=${encodeURIComponent(tableID)}`;

let orderItems = [];//array for order_item objects

function openCart(){
    document.getElementById("cart-container").style.width = "250px";
    document.getElementById("menu-selection").style.marginBottom = "250px";

}
function closeCart(){
    document.getElementById("cart-container").style.width = "0px";
    document.getElementById("menu-selection").style.marginBottom = "0px";
}
function cartPrint(orderItems){
    let cartPrintHTML = document.createElement("ul");
    let totalPriceText = document.createElement("totalPrice");
    let totalPrice = 0;
    for (let j=0; j < orderItems.length; j++) {
        let orderPrintItem = document.createElement("li");
        orderPrintItem.innerText = orderItems[j].name;
        orderPrintItem.setAttribute("orderIndex", j.toString());

        let finalPrice = document.createElement("price");
        let price = orderItems[j].price;

        let removeButton = document.createElement("input");
        removeButton.type = "button";
        removeButton.value = "-";
        removeButton.className = "removeButton";
        removeButton.addEventListener("click", (event) => {
            removeItems(orderPrintItem)
        })

        let orderPrintItemExtra = document.createElement("ul");
        for (let q = 0; q < orderItems[j].selectedOptions.length; q++) {
            let orderPrintItemExtraOptions = document.createElement("li");
            orderPrintItemExtraOptions.innerText = orderItems[j].selectedOptions[q].displayName;
            price += orderItems[j].selectedOptions[q].price;
            orderPrintItemExtra.appendChild(orderPrintItemExtraOptions);
        }
        for (let p = 0; q < orderItems[j].selectedAdditions.length; p++) {
            let orderPrintItemExtraAddition = document.createElement("li");
            orderPrintItemExtraAddition.innerText = orderItems[j].selectedAdditions[p].displayName;
            price += orderItems[j].selectedAdditions[p].price;
            orderPrintItemExtra.appendChild(orderPrintItemExtraAddition);
        }
        finalPrice.innerText = " - Pris: " + price.toString() + " ";
        totalPrice += price;
        orderPrintItem.appendChild(finalPrice);
        orderPrintItem.appendChild(removeButton);
        orderPrintItem.appendChild(orderPrintItemExtra);
        cartPrintHTML.appendChild(orderPrintItem)
    }
    totalPriceText.innerText = "\nPris: " + totalPrice.toString();
    cartPrintHTML.appendChild(totalPriceText);
    document.getElementById("JS-printer").innerHTML = "";
    document.getElementById("JS-printer").appendChild(cartPrintHTML);
}

    function removeItems(orderPrintItem){
        let index = parseInt(orderPrintItem.getAttribute("orderIndex"));
        orderItems.splice(index,1);
        cartPrint(orderItems);
    }
    document.getElementById("cart-button").addEventListener("click",() => cartPrint(orderItems));




//We could avoid having an EventListener if we just load the script after the HTML (so put it at the bottom of <body>)

document.addEventListener("DOMContentLoaded", function () {
    console.log('DOM content loaded');
    //unfinished fetch for getting menu from server
    fetch(menuClassesURL)
        .then(response => {
            return response.json()
        })
        .then(Menu => {
            console.log(Menu);

            document.getElementById("menuContainer").innerHTML = HTMLgen(Menu);
            localStorage.setItem("menuJSON", JSON.stringify(Menu));
        })
        .then( () => {

            //ittereates over option groups to handle checkbox selections
            let optionGroups = document.querySelectorAll('details .option');
            optionGroups.forEach(function (optionGroup) {

                //get the minimum and maximum options form html
                let minSelections = optionGroup.getAttribute('data-min-selections');
                let maxSelections = optionGroup.getAttribute('data-max-selections');

                //adds event listener to each checkbox in the option group
                let checkboxes = optionGroup.querySelectorAll('input[type="checkbox"]');
                checkboxes.forEach(function (checkbox) {
                    checkbox.addEventListener('change', function () {
                        //checks if number of selected checkboxes exceeds the maximum allowed
                        let checkedCount = optionGroup.querySelectorAll('input[type="checkbox"]:checked').length;
                        if (checkedCount > maxSelections) {
                            alert('Maximum selections exceeded.');
                            checkbox.checked = false;
                        }
                    });
                });
            });
        });


    // adds event listener for "send order" button
    let sendOrderButton = document.getElementById('send-order-button');
    sendOrderButton.addEventListener('click', function () {
        console.log('Send Order button clicked');
        // checks if there are orders in the array to send
        if (orderItems.length === 0) {
            console.log('No items in the order.');
            return;
        }

        //Json setup for the orderitems array
        let orderItemsJSON = orderItems.map(orderItem => ({
            name: orderItem.name,
            selectedOptions: orderItem.HTMLoptions,
            selectedAdditions: orderItem.HTMLadditions,
            comment: orderItem.comment
        }));
        console.log("order " + orderItemsJSON);
    //fetch for sending order to server(needs to be fleshed out)
    fetch(sendURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },

        body: JSON.stringify(orderItemsJSON),
    })

        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            // Handle success (if needed)
            console.log('Order sent successfully');
        window.location.replace("/P3_war/"); //Replace with actual IP that we get
        })
        .catch(error => {
            // Handle errors
            console.error('Error sending order:', error);
        })
    })
})
function HTMLgen(Menu) {
    let html = '';
    console.log('Menu:', Menu);
    if (Menu && Menu.length > 0 && Menu[0].items) {
        Menu[0].items.forEach(item => {
            html += '<div class="item-container">';
            html += '<details>'
            html += `<summary>${item.displayName}</summary>`;
            const itemdp = calculateMenuItemDisplayPrice(item);
            html += `<p class="priceTag">${itemdp.amount} for ${itemdp.price} kr</p>`;
            html += item.minOptions<item.maxOptions?`<p class="priceForExtraOptions"><b>Tilvalg: ${itemOptionsMinimumPrice(item)} kr.</b></p>`:'';
            html += `<div class="option" data-min-selections="${item.minOptions}" data-max-selections="${item.maxOptions}">`;



            html += '<h4>Options</h4>' //Change this later IDK the english word right now so SUCK IT
            item.options.forEach(option => {
                html += generateOptionElement(item, option).outerHTML;
            });
            html += '</div>';

            html += '<div class="addition">';
            html += '<h4>Additions</h4>' // Change later because danish name
            item.additions.forEach(addition => {
                html += '<div class="checkbox-container">';
                html += '<label>'
                html += `<input id="additionCheckbox" type="checkbox" data-addition="${addition.displayName}">${addition.displayName}`;
                html += `</label>`;
                html += '</div>';
            })
            html += '<div class="item-comment">';
            html += '<label for="item-comment">Comment:</label>';
            html += '<input type="text" class="item-comment-input" placeholder="kommentare til køkkenet (allegier osv.)">';
            html += '</div>';

            html += '</div>'
            html += `<button class="add-to-order" data-item-name="${item.displayName}">Add to Order</button>`;
            html += '</details>'
            html += '</div>';
        });
    } else {
        console.error('Menu or Menu[0].items is undefined');
    }

    return html;
}

/**
 *
 * @param {MenuItem} item
 * @param {Option} option
 * @return {HTMLDivElement}
 */
function generateOptionElement(item, option){
    const container = document.createElement("div");
    container.className = "checkbox-container";
    container.innerHTML = `<label><input type="checkbox" data-option="${option.displayName}"></label>`;

    const inputLabel = container.querySelector("label");

    inputLabel.innerHTML += option.displayName; //This relies on admin frontend being secure to prevent HTML injection

    const displayPrice = calculateOptionDisplayPrice(item, option);
    if(displayPrice>0)
        container.innerHTML += ` <b>+${calculateOptionDisplayPrice(item, option)} kr.</b>`

    for(let labelText of option.labels){
        let label = document.createElement("span");

        label.classList.add("label")

        switch(labelText){
            case "VEGAN":
                label.innerText = 'VG';
                label.classList.add('veganLabel');
                break;
            case "VEGETARIAN":
                label.innerText = 'V';
                label.classList.add('vegetarianLabel');
                break;
            case "GLUTEN_FREE":
                label.innerText = 'G';
                label.classList.add('glutenFreeLabel');
                break;
            case "LACTOSE_FREE":
                label.innerText = 'L';
                label.classList.add('lactoseFreeLabel');
                break;
            case "FINGER_FOOD":
                label.innerText = 'MF';
                label.classList.add('fingerFoodLabel');
                break;
        }
        container.append(' ');
        container.appendChild(label);
    }

    return container;
}

function showNotification(message, type) {
    const notificationContainer = document.getElementById('notification-container');

    // Create a new notification element
    const notification = document.createElement('div');
    notification.textContent = message;
    notification.className = `notification ${type}`;

    // Append the notification to the container
    notificationContainer.appendChild(notification);

    // Remove the notification after a certain duration
    setTimeout(() => {
        notification.remove();
    }, 3000); // Adjust the duration (in milliseconds) as needed
}
//event listener for "add to order" buttons
document.getElementById("menuContainer").addEventListener("click", function (event) {
    // Check if the clicked element has the "add-to-order" class
    if (event.target.classList.contains("add-to-order")) {
        // Handle the click on the "Add to Order" button
        console.log('Add to Order button clicked');

        // Find the closest item container
        let itemContainer = event.target.closest('.item-container');
        if (!itemContainer) {
            console.error('Could not find the closest .item-container element.');
            return;
        }

        // Find the option group within the item container
        let optionGroup = itemContainer.querySelector('.option');
        if (!optionGroup) {
            console.error('Could not find the .option element within the item container.');
            return;
        }

        //extracts item details and selected options and additions
        let itemName = itemContainer.querySelector('summary').textContent.trim();

        let selectedOptions = [];
        optionGroup.querySelectorAll('input[type="checkbox"]:checked').forEach(function (checkbox) {
            selectedOptions.push(checkbox.getAttribute('data-option'));
        });

        let selectedAdditions = [];
        itemContainer.querySelectorAll('.addition input[type="checkbox"]:checked').forEach(function (checkbox) {
            selectedAdditions.push(checkbox.getAttribute('data-addition'));
        });



        //get comments from the input field on correct item
        let comment = itemContainer.querySelector('.item-comment .item-comment-input').value;
        console.log("komentar" + comment);

        /**
         * @type {MenuItem}
         */
        let item;
        let options = [];
        let additions = [];
        JSON.parse(localStorage.getItem("menuJSON")).forEach(menu => {
            menu.items.forEach(i => {
                if(i.displayName === itemName)
                    item = i;
            })
        });

        for (let optionString of selectedOptions){
            JSON.parse(localStorage.getItem("menuJSON")).forEach(menu => {
                menu.items.forEach(i => {
                    i.options.forEach(option => {
                        if(option.displayName===optionString){
                            options.push(option);
                        }
                    })
                })
            })
        }

        for (let additionString of selectedAdditions){
            JSON.parse(localStorage.getItem("menuJSON")).forEach(menu => {
                menu.items.forEach(i => {
                    i.additions.forEach(addition => {
                        if(addition.displayName===additionString){
                            additions.push(addition);
                        }
                    })
                })
            })
        }


        //console.log(`Item: ${itemName}, PRICE: ${basePrice}`);

        //creates the order_item objects and puts them in the orderItems array
            let orderItem = new Order_Item(itemName);
            orderItem.selectedOptions = options;
            orderItem.selectedAdditions = additions;
            orderItem.HTMLoptions = selectedOptions;
            orderItem.HTMLadditions = selectedAdditions;
            orderItem.price = item.basePrice;
            orderItem.comment = comment.slice();
            console.log("comment again" + comment);
            orderItems.push(orderItem);

        showNotification('Din ordre er tilføjet til kurven!','success')
        console.log(orderItems);
    }
});

function itemOptionsMinimumPrice(item){
    let minPrice = 1_000_000_000_000 //Please do not add any menuitems that cost more than 1 trillion kr
    for (const option of item.options) {
        if(option.price < minPrice)
            minPrice = option.price
    }

    return minPrice
}

/**
 *
 * @param {MenuItem} item
 *
 * @returns {{amount: number, price: number}}
 */
function calculateMenuItemDisplayPrice(item){
    return {
        amount: item.minOptions,
        price: item.basePrice + itemOptionsMinimumPrice(item)*item.minOptions
    }
}

/**
 *
 * @param {MenuItem} item
 * @param {Option} option
 */
function calculateOptionDisplayPrice(item, option){
    return option.price - itemOptionsMinimumPrice(item);
}