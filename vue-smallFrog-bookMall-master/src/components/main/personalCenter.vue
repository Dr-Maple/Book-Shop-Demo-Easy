<template>
    <div >
        <div v-if="personalInfo.member == 'true'" class="memberDiv">
            <h3>尊敬的会员，您好！</h3>
            <el-row :gutter="10">
                <el-col :span="12" :offset="2">您的会员将于  {{date}}  到期，请及时续费</el-col>
                <el-col :span="6" style="font-size: 13px; color:red;">(会员期间您将享受95折优惠)</el-col>
            </el-row>
        </div>
        <div v-else>
            <el-row :gutter="10">
              <el-col :span="12" :offset="2"><h3>您还不是会员</h3></el-col>
              <el-col :span="6" :offset="2"><el-button @click="becomeMember" type="primary">先充值</el-button></el-col>
            </el-row>
            <el-row>
              <el-col :span="12" :offset="2" style="font-size: 13px; color:red;">(成为会员将享受95折优惠)</el-col>
            </el-row>
        </div>
        <div class="personalInfo" style="background-color:#4f6e9d" >
            <el-form status-icon label-width="100px" ref="formInfo" :model="formInfo" :rules="rules" label-position="right">
                <el-form-item>
                    <el-row>
                        <el-col v-show="this.editStatus">
                            <el-button type="primary" icon="el-icon-edit" circle @click="changeInputStatus()"></el-button>
                        </el-col>
                        <el-col v-show="!this.editStatus">
                            <el-button type="success" icon="el-icon-check" circle @click="submitPersonalInfo()"></el-button>
                        </el-col>
                    </el-row>
                </el-form-item>
                <el-form-item prop="profilePhoto" label="头像：">
                    <el-row type="flex">
                        <el-col><el-upload action="http://localhost:8080/uploadProfilePhoto" :on-success="changeProfilePhoto" :show-file-list="false">
                            <el-avatar shape="square" :size="100" fit="fill" :src="'../../../static/profilePhoto/' + formInfo.profilePhoto" :key="formInfo.profilePhoto"></el-avatar>   
                        </el-upload></el-col>
                    </el-row>
                </el-form-item>
                <el-form-item prop="username" label="昵称：">
                    <el-input class="personInfoInput" v-model="formInfo.username" :disabled="this.editStatus"></el-input>
                </el-form-item>
                <el-form-item prop="sex" label="性别：">
                    <el-radio :disabled="this.editStatus" v-model="formInfo.sex" label="man">男</el-radio>
                    <el-radio :disabled="this.editStatus" v-model="formInfo.sex" label="woman">女</el-radio>
                </el-form-item>
                <el-form-item label="出生日期：">
                    <el-date-picker :disabled="this.editStatus" v-model="formInfo.birthday" type="date" placeholder="选择日期" format="yyyy-MM-dd" value-format="yyyy-MM-dd"> </el-date-picker>
                </el-form-item>
                <el-form-item prop="tel" label="手机号：">
                    <el-input class="personInfoInput" v-model="formInfo.tel" :disabled="this.editStatus"></el-input>
                </el-form-item>
                
                <el-form-item prop="email" label="邮箱：">
                    <el-input class="personInfoInput" v-model="formInfo.email" :disabled="this.editStatus"></el-input>
                </el-form-item>

                <el-form-item prop="description" label="简介：">
                    <el-input type="textarea" class="personInfoInput" v-model="this.personalInfo.description" :disabled="this.editStatus"></el-input>   
                </el-form-item>
            </el-form>
        </div>
    </div>

</template>

<script>
import axios from "axios";
import 'element-ui/lib/theme-chalk/display.css';
import moment from "moment";
export default {
    
    data(){
        // <!--验证手机号是否合法-->
        let checkTel = (rule, value, callback) => {
            if (!this.checkMobile(value)) {
                callback(new Error('请输入正确的手机号'))
            } else {
                callback() 
            }
        }
        let checkBirthday = (rule, value, callback) =>{
            var date = new Date()
            if(value >= date){
                callback(new Error('请输入正确的日期'))
            }else{
                callback();
            }
        }
        return{
            personalInfo: {
                id:"",
                username: "",
                email: "",
                password: "",
                tel: "",
                profilePhoto: "",
                description: "",
                memberDate: "",
                member: "",
                sex: "",
                birthday: "",
                birthday: "",
            },
            formInfo: {},
            imgUrl: "",
            editStatus: true,
            date: "",
            rules:{
                tel:[{
                    validator: checkTel,
                    trigger: 'change'
                }],
                birthday:[{
                    validator: checkBirthday,
                    trigger: 'change'
                }]
            }
        }

    },
    created(){
        this.getPersonalInfo()
    },
    methods:{
        // 验证手机号
        checkMobile (str) {
            let re = /^1\d{10}$/
            if (re.test(str)) {
                return true
            } else {
                return false
            }
        },
        becomeMember(){
            this.$confirm('是否确认充值?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$message({
                    type: 'success',
                    message: '充值成功!'
                });
                axios.post("/becomeMember",this.personalInfo).then(res=>{
                    this.getPersonalInfo()
                })
                this.$cookies.set('user', this.personalInfo)
                this.$forceUpdate()
                // console.log(this.$cookies.get('user'))
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消充值'
                });          
            });
        },
        getPersonalInfo(){
            this.personalInfo = this.$cookies.get('user')
            axios.post("/getCustomerInfo",{
                id : this.$cookies.get('user').id
            }).then(res => {
                this.personalInfo = res.data.data;
                // console.log(this.personalInfo.profilePhoto)
                this.formInfo = this.personalInfo;
                this.$forceUpdate()
                console.log(this.formInfo);
                this.$cookies.set('user',this.personalInfo)
                this.$forceUpdate()
                // console.log("cookies", this.$cookies.get('user'))
                var dateTest = moment(this.personalInfo.memberDate).add(1,'months')
                this.date = dateTest.format("YYYY-MM-DD")
            })
        },
        changeInputStatus(){
            this.editStatus = !this.editStatus;
        },
        submitPersonalInfo(){
            
            axios.post("/changeCustomerInfo", this.personalInfo).then(res=>{
                // console.log("test")
                this.changeInputStatus();
                this.getPersonalInfo()
            })
        },
        changeProfilePhoto(res, file){
            this.personalInfo.profilePhoto = file.name;
            this.formInfo.profilePhoto = this.personalInfo.profilePhoto;
            this.$cookies.set("user", this.personalInfo)
            this.$forceUpdate()
            axios.post("/changeCustomerInfo",this.personalInfo).then(res=>{
                console.log("success")
                location.reload()
            })
        },
        errorHandler(){
            return true
        }
    },
}
</script>

<style>
.personalInfo{
    width: 500px;
    margin: auto;
    margin-top: 50px;
    padding: 20px 60px 20px 40px;
    border-radius: 10px;
}
.memberDiv{
    /* background-color: rgb(97, 185, 75); */
    border-radius: 10px;
    padding: 10px 0px 20px 30px;
}
.personalInfo .el-form-item__label{
    color: white;
}
</style>