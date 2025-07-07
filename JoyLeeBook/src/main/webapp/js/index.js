const menu = document.getElementById('menu')
const userProfile = document.getElementById('user-profile')


window.addEventListener('load', () => {
    pageNavigation(3, 3, 5)

    displayElement({
        name: menu,
        icon: 'menu-icon',
        isShw: false
    })

    displayElement({
        name: userProfile,
        ico: 'user-profile-icon',
        isShow: false
    })

    document.addEventListener("contextmenu", function (e) {
        e.preventDefault();
    });


    document.getElementById("sub-authentication-layout")?.addEventListener("mouseover", function () {
        console.log(123)
    })
})



const displayElement = (element) => {
    /*
     Notes:
     element['name']: Main element's tag which is got by document.getElementById()
     element['icon']: Icon's ID String which will handle 'click' event to show main element's tag
     element['isShow']: Status of main element's tag
     */

    if (element['name'] === null || element['icon'] === null)
        return

    document.getElementById(element['icon']).addEventListener('click', () => {

        if (element['isShow']) {
            element['name'].classList.remove('d-flex')
            element['isShow'] = false
        } else {
            element['name'].classList.add('d-flex')
            element['isShow'] = true
        }
    })
}

const pageNavigation = (totalStory, currentPage, limitPageNumber) => {
    if (document.getElementById('page-navigation') === null) {
        return
    }

    currentPage > totalStory ? () => {
        currentPage = totalStory
    } : ''

    const navigation = document.getElementById('page-navigation')

    if (currentPage > 1) {
        navigation.innerHTML += `
            <a href="#${currentPage - 2}" class="page-number d-flex align-items-center justify-content-center"><</a>
            <a name="${currentPage - 2}" href="#${currentPage - 1}" class="page-number d-flex align-items-center justify-content-center">${currentPage - 1}</a>
            `
    }

    for (var i = currentPage; i <= totalStory; i++) {
        navigation.innerHTML += `
            <a name="${i}" href="#${i}" class="page-number d-flex align-items-center justify-content-center">${i}</a>
            `
    }

    navigation.querySelector(`a[name="${currentPage}"]`).classList.add('page-number-active')

    if (currentPage < totalStory) {
        navigation.innerHTML += `
            <a href="#${currentPage + 1}" class="page-number d-flex align-items-center justify-content-center">></a>
            `
    }
}