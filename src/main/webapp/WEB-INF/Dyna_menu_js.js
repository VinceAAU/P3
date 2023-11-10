class Order_Item {
  constructor(name) {
    this.name = name;
    this.selectedOptions = [];
    this.selectedAdditions = [];
  }
}

const order = [];

function addToOrder(itemName, selectedOptions, selectedAdditions) {
  const orderItem = new Order_Item(itemName);
  orderItem.selectedOptions = selectedOptions;
  orderItem.selectedAdditions = selectedAdditions;
  order.push(orderItem);

  console.log('item added to order', orderItem);
}

document.querySelector('div > button.add-to-order').addEventListener('click', (event) => {
  const target = event.target;

  if (target.classList.contains('add-to-order')) {
    const itemName = target.getAttribute('data-item-name');
    const optionsGroup = document.querySelector('.option');
    const selectedOptions = getSelectedOptions(optionsGroup);
    const additionGroup = document.querySelector('.addition');
    const selectedAdditions = getSelectedAdditions(additionGroup);

    addToOrder(itemName, selectedOptions, selectedAdditions);
  }

  function getSelectedOptions(group) {
    const buttons = group.querySelectorAll('button');
    const selectedOptions = [];

    buttons.forEach((button) => {
      if (button.classList.contains('selected')) {
        const option = button.getAttribute('data-option');
        selectedOptions.push(option);
      }
    });

    return selectedOptions;
  }

  function getSelectedAdditions(group) {
    const buttons = group.querySelectorAll('button');
    const selectedAdditions = [];

    buttons.forEach((button) => {
      if (button.classList.contains('selected')) {
        const addition = button.getAttribute('data-addition');
        selectedAdditions.push(addition);
      }
    });

    return selectedAdditions;
  }

  const optionsGroup = document.querySelector('.option');
  if (optionsGroup) {
    const buttons = optionsGroup.querySelectorAll('button');
    const minSelections = parseInt(optionsGroup.getAttribute('data-min-selections'));
    const maxSelections = parseInt(optionsGroup.getAttribute('data-max-selections'));

    const selectedOptions = new Set();

    buttons.forEach((button) => {
      button.addEventListener('click', () => {
        const option = button.getAttribute('data-option');
        console.log('option clicked');

        if (selectedOptions.has(option)) {
          selectedOptions.delete(option);
          button.classList.remove('selected');
        } else {
          if (selectedOptions.size < maxSelections) {
            selectedOptions.add(option);
            button.classList.add('selected');
          }
        }
      });
    });
  }

  const additionsGroup = document.querySelector('.addition');
  if (additionsGroup) {
    const buttons = additionsGroup.querySelectorAll('button');

    const selectedAdditions = new Set();

    buttons.forEach((button) => {
      button.addEventListener('click', () => {
        const addition = button.getAttribute('data-addition');
        console.log('addition clicked');

        if (selectedAdditions.has(addition)) {
          selectedAdditions.delete(addition);
          button.classList.remove('selected');
        } else {
          selectedAdditions.add(addition);
          button.classList.add('selected');
        }
      });
    });
  }
});