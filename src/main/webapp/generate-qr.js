const submitButton = document.querySelector("#qrSubmitButton");
const form = document.querySelector('#qrForm')

console.log('Line 3');

submitButton.addEventListener('click', (e) => {
    console.log('Clicked on button')
    e.preventDefault();

    const url = new URL("http://70.34.203.118:8080/P3_war/Dyna_menu.html");

    url.searchParams.set(
        'restaurant',
        form.restaurant.value
    );
    url.searchParams.set(
        'tableId',
        document.getElementById("tableIdInput").value
    );

    const container = document.querySelector('#qrContainer');
    container.innerHTML = "";
    new QRCode(container, url.toString());
    container.hidden = false;
})