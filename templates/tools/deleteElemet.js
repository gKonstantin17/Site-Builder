function removeElementById(id) {
    if (!id)
    {
        return;
    }
    var element = document.getElementById(id);  // Находим элемент по id
    if (element) {  // Если элемент найден
        element.remove();  // Удаляем элемент из DOM
    } 
}
