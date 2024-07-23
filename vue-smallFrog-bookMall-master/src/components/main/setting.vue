<template>
<el-container class="test">
    <el-aside width="250px">
        <el-card class="leftNav">
            <img :src="'../../../static/profilePhoto/' + this.$cookies.get('user').profilePhoto" class="leftImg" :key="this.$cookies.get('user').profilePhoto"/>
            <div class="navItem" :class="index == showIndex ? 'cur' : ''" v-for="(item, index) in navItems" :key="index" @click="showInfo(index)">{{ item }}</div>
        </el-card>
    </el-aside>
    <el-main >
        <router-view></router-view>
    </el-main>
</el-container>
</template>

<script>
    import 'element-ui/lib/theme-chalk/display.css';
    import 'vue-area-linkage/dist/index.css'
    export default {
        data(){
            return{
                navItems : ["个人信息", "收货地址","修改密码"],
                showIndex: 0,
            }
        },
        methods: {
            handleChange(value){
                console.log(value)
            },
            showInfo(e){
            this.showIndex = e
            if(e == 0){
                this.$router.push({
                    path: "/setting/personalCenter"
                })
            }else if(e == 1){
                this.$router.push({
                    path: "/setting/editAddress"
                })
            }else if(e == 2){
                this.$router.push({
                    name: 'updatePassword'
                })
            }
            
        },
        }
    }
</script>

<style>
.leftNav {
    top: 50%;
    width: 150px;
    height: 300px;
    margin-top: -180px;
    margin-left: 60px;
    position: fixed;
    border-radius: 20px;
    background-color: #4f6e9d;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}

.leftNav .leftImg {
    width: 100%;
    margin-bottom: 20px;
    color: #ffffff;
    position: relative;
    border-radius: 12px;
}

.leftNav .navItem {
    text-align: center;
    height: 60px;
    cursor: pointer;
    color: #ffffff;
    cursor: pointer;
    position: relative;
}

a {
    text-decoration:none ; 
    color: #ffffff;
}

.router-link-active {
    color: #4f6e9d;
    border-radius: 10px 10px 10px 10px;
    background-color: #ffffff;
}
.leftNav .navItem:hover {
    transform: scale(1.05);
}

.leftNav .navItem.cur::after {
    color: #ffffff;
    content: "";
    width: 5px;
    height: 22px;
    border-radius: 10px 10px 10px 10px;
    position: absolute;
    background-color: currentColor;
    top: 0;
    right: 0;
    margin: auto;
}
.profilePhoto{
    max-height: 100px;
}

.logBtn {
    width: 150px;
    height: 40px;
    border-radius: 25px;
    border: none;
    outline: none;
    background-color: #4F6E9D;
    color: #FFFFFF;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
}

.logBtn:focus,
.logBtn:hover {
    color: #FFFFFF;
    border-color: #7E9DCA;
    background-color: #7E9DCA;
}

.logBtn:active {
    color: #FFFFFF;
    border-color: #7E9DCA;
    outline: 0;
}

.test{
    height: 740px;
     /* background: #fff url("./../../../static/20220520004930.jpg") no-repeat; */
     background-size: cover;
}
</style>
