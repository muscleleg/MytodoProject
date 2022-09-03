
const todolistbutton=document.querySelector("div.todolist__sidebar__todolistbutton");
const searchbutton=document.querySelector("div.todolist__sidebar__searchbutton");
const sidebarpast=document.querySelector("div.todolist__sidebarpast");
const sidebarsearch=document.querySelector("div.todolist__sidebarsearch");
Date.prototype.toDateInputValue = (function() {
    var local = new Date(this);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(0,10);
});
function handle_button_Enter(b){
    // b.style.backgroundColor ="#a9a9a9";
    b.classList.toggle("buttonEnter");
    console.log("entertest");
}
function handle_button_leave(b){
    // b.style.backgroundColor ="#dee1e6";
    b.classList.toggle("buttonEnter");
    console.log("leavetest");
}
function handle_button_click(b1,b2,s1,s2){
    b1.classList.toggle("buttonClick");
    b2.classList.remove("buttonClick");

    s1.classList.toggle("visible");
    s2.classList.remove("visible");

}

// todolistbutton.addEventListener("mouseenter",() =>handle_button_Enter(todolistbutton));
// todolistbutton.addEventListener("mouseleave",() =>handle_button_leave(todolistbutton));
todolistbutton.addEventListener("click",() =>handle_button_click(todolistbutton,searchbutton,sidebarpast,sidebarsearch));


// searchbutton.addEventListener("mouseenter",() =>handle_button_Enter(searchbutton));
// searchbutton.addEventListener("mouseleave",() =>handle_button_leave(searchbutton));
searchbutton.addEventListener("click",() =>handle_button_click(searchbutton,todolistbutton,sidebarsearch,sidebarpast));
document.getElementById('date').value = new Date().toDateInputValue();


// h1.addEventListener("click",handleTitleClick);