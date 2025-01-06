function loaderFromJava(id, stylesJson) {
    var element = document.getElementById(id);
    if (element) {
        if (stylesJson.text !== undefined) {
            element.textContent = stylesJson.text;
        }
        Object.keys(stylesJson).forEach(function(styleProperty) {
            element.style[styleProperty] = stylesJson[styleProperty];
        });
        if (!element.querySelector('.resizer')) {
            addResizers(element);
        }
    } else {
        console.error("Элемент с id " + id + " не найден.");
    }
}

function addResizers(element) {
    const resizers = [
        'top', 'bottom', 'left', 'right',
        'top-left', 'top-right', 'bottom-left', 'bottom-right'
    ];
    resizers.forEach(resizer => {
        let resizerDiv = document.createElement('div');
        resizerDiv.classList.add('resizer', resizer);
        resizerDiv.dataset.id = element.id;
        element.appendChild(resizerDiv);
    });
}
function initLoaderFromJava() {}