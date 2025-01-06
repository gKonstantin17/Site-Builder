let previouslySelectedElement = null; // Хранит ранее выбранный элемент
let selectedElementId = null; // Хранит ID выбранного контейнера (или null)

function handleElementSelector(event) {
    const element = event.target; // Получаем элемент, по которому кликнули
    const id = element.id; 
    const tagName = element.tagName; 
    const textContent = element.textContent.trim(); 

    // Очистка стилей ранее выбранного элемента
    if (previouslySelectedElement) {
        previouslySelectedElement.style.outline = ''; // Сбрасываем стиль рамки
    }

    // Устанавливаем стиль рамки для нового выбранного элемента
    element.style.outline = '2px solid #007BFF';
    previouslySelectedElement = element;

    // Проверяем, является ли элемент допустимым контейнером и имеет ли он ID
    if (['DIV', 'FORM', 'HEADER'].includes(tagName) && id) {
        selectedElementId = id; // Обновляем ID выбранного элемента
    } else {
        selectedElementId = null; // Сбрасываем, если элемент не подходит
    }

    // Дополнительная информация об элементе
    const cssText = element.style.cssText;
    const styleList = {};
    if (cssText) {
        const styles = cssText.split(';').map(style => style.trim()).filter(style => style);
        styles.forEach(style => {
            const [key, value] = style.split(':').map(part => part.trim());
            if (key && value) {
                styleList[key] = value;
            }
        });
    }

    // Создаем объект с информацией об элементе
    const elementInfo = {
        id: id,
        tagName: tagName,
        text: textContent || "(пусто)",
        styles: styleList
    };

    // Отправляем информацию в Java через CEF
    if (window.cefQuery) {
        window.cefQuery({
            request: "logMessage: " + JSON.stringify(elementInfo),
            onSuccess: function(response) {
            },
            onFailure: function(error_code, error_message) {
            }
        });
    }
}

// Инициализация события клика
function initElementSelector() {
    document.addEventListener('click', handleElementSelector);
}

