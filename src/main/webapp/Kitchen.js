const container = document.getElementById('arrayContainer');
const KitchenUrl ="/P3_war/OrderSent"

for (let i = 0; i < 6; i++) {
    const gridItem = document.createElement('div');
    gridItem.className = 'grid-item';
    gridItem.innerHTML = generateHtmlContent(i);
    container.appendChild(gridItem);
}
document.addEventListener("DOMContentLoaded", function (){
    setInterval(fetchOrders,20000);
})


function generateHtmlContent(index) {
    //generate order here
    return `<p>Order ${index+1}</p>`;
}
function fetchOrders() {
    console.log("fetch made");
    fetch(KitchenUrl)
        .then(response=>{
            if(!response.ok) {
                throw new Error('order fetch failed');
            }
            return response.json();
        })
        .then(data => {
            if (data.message === "No order yet"){
                console.log("order received")

            }else{
                console.log("orders received")

            }

        })
        .catch(error => {
            console.error('fetch error:', error);
        });
}
