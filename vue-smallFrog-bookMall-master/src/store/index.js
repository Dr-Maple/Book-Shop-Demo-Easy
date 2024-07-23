//该文件用于创建Vuex中最为核心的store

import axios from 'axios'
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
//准备actions-用于响应组件中的动作
const actions = {
    bookName(context, bookName){
        context.commit('SELECTBOOKSINFO', bookName)
        console.log(bookName)
    }
}

//准备mutations-用于操作数据（state）
const mutations = {
    SELECTBOOKSINFO(context, bookName){
        state.selectBookName  = bookName;
        axios.post("/getBooksByName", {
            'bookName' : bookName
        }).then(res=>{
            state.booksInfo = res.data.data
        })
    }
}

//准备state-用于存储数据
const state = {
    selectBookName: "",
    booksInfo: [],
}

//创建store 并 导出（暴露）store
export default new Vuex.Store({
    actions,
    mutations,
    state,
})
