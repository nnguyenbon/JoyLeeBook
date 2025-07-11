const menu = document.getElementById('menu')
const userProfile = document.getElementById('user-profile')


window.addEventListener('load', () => {

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

//    document.addEventListener("contextmenu", function (e) {
//        e.preventDefault();
//    });

})



const displayElement = (element) => {
    /*
     Notes:
     element['name']: Main element's tag which is got by document.getElementById()
     element['icon']: Icon's ID String which will handle 'click' event to show main element's tag
     element['isShow']: Status of main element's tag
     */

    if (element['name'] === null || element['icon'] === null) return

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