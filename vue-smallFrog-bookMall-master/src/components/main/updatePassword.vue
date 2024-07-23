<template>
    <div class="updatePasswordDiv">
        <el-form :rules="rules" :model="ruleForm" label-position="right" label-width="100px" ref="ruleForm">
            <el-form-item label="原密码：" prop="oldPassword"><el-input type="password" autocomplete="off" v-model="ruleForm.oldPassword"></el-input></el-form-item>
            <el-form-item label="新密码：" prop="newPassword"><el-input type="password" autocomplete="off" v-model="ruleForm.newPassword"></el-input></el-form-item>
            <el-form-item label="确认密码：" prop="checkPassword"><el-input type="password" autocomplete="off" v-model="ruleForm.checkPassword"></el-input></el-form-item>
            <el-form-item>
                <el-button @click="modifyPasswordDialog = false">取 消</el-button>
                <el-button type="primary" @click="modifyPassword('ruleForm')">确 定</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    data(){
        var validateOldPass = (rule, value, callback) => {
            if(value == ''){
                callback(new Error('请输入原始密码'))
            }else{
                if(value !== this.adminInfo.password){
                    callback(new Error('密码错误，请再次输入密码'))
                }
                callback();
            }
        };
        var validateNewPass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (this.ruleForm.checkPassword !== '') {
                    this.$refs['ruleForm'].validateField('checkPassword');
                }
                callback();
            }
        };
        var validateCheckPass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.ruleForm.newPassword) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                callback();
            }
        };

        return{
            rules:{
                oldPassword: [
                    {  required: true, validator: validateOldPass, trigger: 'blur' },
                ],
                newPassword: [
                    { required: true, validator: validateNewPass, trigger: 'blur' },
                ],
                checkPassword:[
                    { required: true, validator: validateCheckPass, trigger: 'blur' },
                ]
            },
            ruleForm:{
                oldPassword: "",
                newPassword: "",
                checkPassword: "",
            },
            adminInfo: {},
        }
    },
    created(){
        this.adminInfo = this.$cookies.get('user')
    },
    methods:{
        modifyPassword(formName){
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    alert('submit!');
                    this.adminInfo.password = this.ruleForm.newPassword;
                    this.$cookies.set('user', this.adminInfo)
                    this.updateCustomerPassword()
                    this.modifyPasswordDialog = false
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        updateCustomerPassword(){
            axios.post("/changeCustomerInfo", this.adminInfo).then(res=>{

            })
        },
    }
}
</script>

<style>
.updatePasswordDiv{
    width: 400px;
    text-align: center;
    background-color:#4f6e9d;
    padding: 20px;
    /* top: 50%; */
    margin: auto;
    transform: translateY(50%);
    padding: 30px 60px 20px 40px;
    border-radius: 10px;
}
.updatePasswordDiv .el-form-item__label{
    color: white;
}
</style>