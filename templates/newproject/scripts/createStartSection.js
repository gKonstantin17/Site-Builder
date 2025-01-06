function createStartSection() {
    const id = crypto.randomUUID();
    const newSection = document.createElement('section');
    newSection.id = id;

    const containerDiv = document.createElement('div');
    containerDiv.className = 'container';

    newSection.appendChild(containerDiv);
    document.body.appendChild(newSection);
}