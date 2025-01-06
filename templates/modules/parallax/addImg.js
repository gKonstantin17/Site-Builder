function addImage(value) {
    // Создание элемента <img>
    var imgElement = document.createElement('img');
    
    // Установка атрибутов
    imgElement.src = 'https://www.ccsidbb.catholic.edu.au/wp-content/uploads/2020/06/Shrek.jpg';  // Установка ссылки на изображение
    imgElement.className = 'mouse';
    imgElement.setAttribute('value', value); // число
    
    // Находим контейнер с классом .container
    var container = document.querySelector('.container');
    
    // Добавляем изображение в контейнер
    container.appendChild(imgElement);
}
addImage(2);