

const todolistbutton=document.querySelector("div.todolist__sidebar__todolistbutton");
const searchbutton=document.querySelector("div.todolist__sidebar__searchbutton");
const sidebarpast=document.querySelector("div.todolist__sidebarpast");
const sidebarsearch=document.querySelector("div.todolist__sidebarsearch");
const todolist__contentcontainerForm=document.querySelector("div.todolist__contentcontainer__form");
const todolist__addbutton=document.querySelector("div.todolist__contentcontainer__todolist__addbutton");
const todolist__formcancelbutton=document.querySelector("div.todolist__contentcontainer__form__buttons__cancel");
const content=document.querySelector("div.todolist__contentcontainer__todolist");

//=================달력날짜 현재 날짜로 변경=========================//
// Date.prototype.toDateInputValue = (function() {
//     var local = new Date(this);
//     local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
//     return local.toJSON().slice(0,10);
// });
// document.getElementById('date').value = new Date().toDateInputValue();
//================================================================//
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
    // b1.classList.toggle("buttonClick");
    // b2.classList.remove("buttonClick");

    s1.classList.toggle("formvisible");
    s2.classList.remove("formvisible");

}
function handle_button_clickForm(form){
    form.classList.toggle("formvisible");

}
function handle_button_cancelForm(form){
    form.classList.toggle("formvisible");
    const textarea = document.querySelector("div.todolist__contentcontainer__form textarea");
    textarea.value = "";
}
function allClose(){
    todolist__contentcontainerForm.classList.remove("formvisible");
    sidebarpast.classList.remove("formvisible");
    sidebarsearch.classList.remove("formvisible");
}
/**
 * 사이드바 조작 js
 */
// todolistbutton.addEventListener("mouseenter",() =>handle_button_Enter(todolistbutton));
// todolistbutton.addEventListener("mouseleave",() =>handle_button_leave(todolistbutton));
todolistbutton.addEventListener("click",() =>handle_button_click(todolistbutton,searchbutton,sidebarpast,sidebarsearch));


// searchbutton.addEventListener("mouseenter",() =>handle_button_Enter(searchbutton));
// searchbutton.addEventListener("mouseleave",() =>handle_button_leave(searchbutton));
searchbutton.addEventListener("click",() =>handle_button_click(searchbutton,todolistbutton,sidebarsearch,sidebarpast));


// h1.addEventListener("click",handleTitleClick);

/**
 * form 조작 js
 */
 todolist__addbutton.addEventListener("click",() =>handle_button_clickForm(todolist__contentcontainerForm));
 todolist__formcancelbutton.addEventListener("click",() =>handle_button_cancelForm(todolist__contentcontainerForm));

 /**
  * form 끄기
  */
  content.addEventListener("click",allClose);