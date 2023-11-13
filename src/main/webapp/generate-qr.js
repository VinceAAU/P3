const submitButton = document.querySelector("#qrSubmitButton");
const form = document.querySelector('#qrForm')

console.log('Line 3');

submitButton.addEventListener('click', (e) => {
    console.log('Clicked on button')
    e.preventDefault();

    const url = new URL("https://example.com/bestil");

    url.searchParams.set(
        'restaurant',
        form.restaurant.value
    );
    url.searchParams.set(
        'tableId',
        form.tableId.value
    );

    const container = document.querySelector('#qrContainer');
    container.innerHTML = "";
    new QRCode(container, url.toString());
    container.hidden = false;
})