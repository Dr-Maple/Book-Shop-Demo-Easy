<template>
    <div>
        <el-row type="flex">
            <el-col :span="4">
                <el-image src="../../../static/cateNav.png" fit="fill"></el-image>
            </el-col>
            <el-col :offset="2">
                <el-row><h2>后台管理</h2></el-row>
                <el-row :gutter="20">
                    <el-col :span="6">
                        <el-input v-model="searchBookName" placeholder="请输入书名"></el-input>
                    </el-col>
                    <el-col :span="6">
                        <el-input v-model="searchBookAuthor" placeholder="请输入作者名"></el-input>
                    </el-col>
                    <el-col :span="6" :offset="6">
                        <el-row type="flex">
                            <el-col :span="10"><el-button icon="el-icon-search" type="primary" @click="searchBookInfo()">搜索</el-button></el-col>
                            <el-col :span="10"><el-button icon="el-icon-plus" type="primary" @click="addBookInfo()">添加</el-button></el-col>
                        </el-row>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
        <el-row>
            <el-table :data="bookInfo">
                <el-table-column header-align="center" align="center" prop="name" label="书名" fixed="left" width="120"></el-table-column>
                <el-table-column header-align="center" align="center" prop="imgUrl" label="图片" width="120">
                    <template slot-scope="scope">
                        <el-image :src="'../../../static/booksImage/' + scope.row.imgUrl" style="height:80px;"></el-image>
                    </template>
                </el-table-column>
                
                <el-table-column header-align="center" align="center" prop="author" label="作者" width="120"></el-table-column>
                <el-table-column header-align="center" align="center" prop="newPrice" label="押金" width="120"></el-table-column>
                <el-table-column header-align="center" align="center" prop="type" label="图书类型" width="200" :filters="bookType" :filter-method="filterHandler"></el-table-column>
                <el-table-column header-align="center" align="center" prop="inventory" label="库存" width="120"></el-table-column>
                <el-table-column header-align="center" align="center" prop="sold" label="已借" width="120"></el-table-column>
                <el-table-column header-align="center" align="center" prop="date" label="出版时间" width="120"></el-table-column>
                <el-table-column header-align="center" align="center" prop="description" label="简介" ></el-table-column>
                <el-table-column header-align="center" align="center" label="操作" fixed="right">
                    <template slot-scope="scope">
                        <el-button @click.native.prevent="editBookInfo(scope.$index, scope.row)" type="text" size="small"> 编辑 </el-button>
                        <el-button @click.native.prevent="updateBookState(scope.$index, scope.row)" type="text" size="small">
                            <p v-show="scope.row.state == 'NOT_ON_SALE'" style="color: red;">上架</p>
                            <p v-show="scope.row.state == 'ON_SALE'" >下架</p>
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>
        <el-dialog :title="dialogFormTitle" :visible.sync="dialogFormVisible">
            <el-form :model="oneBookInfo" ref="oneBookInfo">
                <el-form-item label="书名" :label-width="formLabelWidth">
                    <el-input v-model="oneBookInfo.name" autocomplete="off" ></el-input>
                </el-form-item>
                <el-form-item label="图片" :label-width="formLabelWidth">
                    <el-upload class="bookImage-uploader" action="http://localhost:8080/uploadBookImage" :show-file-list="false" :on-success="handleAvatarSuccess">
                        <img v-if="oneBookInfo.imgUrl" :src="'../../../static/booksImage/'+ this.oneBookInfo.imgUrl" class="bookImage">
                        <i v-else class="el-icon-plus bookImage-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
                <el-form-item label="作者" :label-width="formLabelWidth">
                    <el-input v-model="oneBookInfo.author" autocomplete="off" ></el-input>
                </el-form-item>
                <el-form-item label="押金" :label-width="formLabelWidth">
                    <el-input v-model="oneBookInfo.newPrice" autocomplete="off" ></el-input>
                </el-form-item>
                <el-form-item label="图书类型" :label-width="formLabelWidth">
                <el-select v-model="oneBookInfo.type" placeholder="请选择图书类型" :label-width="formLabelWidth">
                    <el-option label="文学类" value="0"></el-option>
                    <el-option label="童书" value="1"></el-option>
                    <el-option label="教育类" value="2"></el-option>
                    <el-option label="考试类" value="3"></el-option>
                    <el-option label="人文社科类" value="4"></el-option>
                    <el-option label="经管综合类" value="5"></el-option>
                    <el-option label="生活类" value="6"></el-option>
                    <el-option label="艺术类" value="7"></el-option>
                    <el-option label="科技类" value="8"></el-option>
                    <el-option label="其他" value="9"></el-option>
                </el-select>
                </el-form-item>
                <el-form-item label="库存" :label-width="formLabelWidth">
                    <el-input-number v-model="oneBookInfo.inventory" :min="1"></el-input-number>
                </el-form-item>
                <el-form-item label="状态" :label-width="formLabelWidth">
                    <el-switch v-model="oneBookInfo.state" active-value="ON_SALE" inactive-value="NOT_ON_SALE" active-text="在售" inactive-text="已下架"></el-switch>
                </el-form-item>
                <el-form-item label="出版时间" :label-width="formLabelWidth">
                    <el-date-picker v-model="oneBookInfo.date" type="date" placeholder="选择日期时间" format="yyyy-MM-dd" value-format="yyyy-MM-dd"> </el-date-picker>
                </el-form-item>
                <el-form-item label="简介" :label-width="formLabelWidth">
                    <el-input v-model="oneBookInfo.description" autocomplete="off" ></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitForm()">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import 'element-ui/lib/theme-chalk/display.css'
import axios from 'axios'
export default {
    data(){
        return{
            options: [{
                value: '0',label: '文学类'}, 
                {value: '1',label: '童书'}, 
                {value: '2',label: '教育类'}, 
                {value: '3',label: '考试类'}, 
                {value: '4',label: '人文社科类'},
                {value: '5',label: '经管综合类'},
                {value: '6',label: '生活类'},
                {value: '7',label: '艺术类'},
                {value: '8',label: '科技类'},
                {value: '9',label: '其他'}
            ],
            bookType: [{
                value: '文学类',text: '文学类'}, 
                {value: '童书',text: '童书'}, 
                {value: '教育类',text: '教育类'}, 
                {value: '考试类',text: '考试类'}, 
                {value: '人文社科类',text: '人文社科类'},
                {value: '经管综合类',text: '经管综合类'},
                {value: '生活类',text: '生活类'},
                {value: '艺术类',text: '艺术类'},
                {value: '科技类',text: '科技类'},
                {value: '其他',text: '其他'}
            ],
            bookInfo: [{}],
            searchBookName: "",
            searchBookAuthor: "",
            selectBookType: "",
            dialogFormVisible: false,
            oneBookInfo: {
                name:"",
                author:"",
                imgUrl:"",
                inventory: 1,
                newPrice:"",
                type:"",
                date:"",
                description:"",
                sold:0
            },
            formLabelWidth: "100px",
            dialogFormTitle:"",
        }
    },
    created(){
        this.getAllBooksInfo()
        console.log(this.oneBookInfo)
    },
    methods:{
        filterHandler(value, row, column) {
            const property = column['property'];
            return row[property] === value;
        },
        getAllBooksInfo(){
            axios.post("/getAllBooks").then(res=>{
                this.bookInfo = res.data.data
                console.log(this.bookInfo)
            })
        },
        editBookInfo(index, row){
            this.oneBookInfo = Object.assign({}, row)
            this.dialogFormVisible = true
            this.dialogFormTitle = "编辑图书信息"
        },
        updateBookState(index, row){
            axios.post("/updateBookState",row).then(res=>{
                if(res.data.flag){
                    this.getAllBooksInfo()
                }
            })
        },
        addBookInfo(){
            this.oneBookInfo = {}
            this.dialogFormVisible = true 
            this.dialogFormTitle = "添加图书信息"
            this.$set(this.oneBookInfo, 'inventory', 1)
            this.$set(this.oneBookInfo, 'sold', 0)
            this.$set(this.oneBookInfo, 'state', "ON_SALE")
            console.log(this.oneBookInfo)
        },
        submitForm(){
            console.log(this.oneBookInfo)
            this.dialogFormVisible = false
            axios.post("/editBookInfo", this.oneBookInfo).then(res=>{
                console.log("success")
            })
            this.getAllBooksInfo()
        },
        searchBookInfo(){
            axios({
                method: 'get',
                url: "/searchBookInfo",
                params: {
                    'name': this.searchBookName,
                    'author': this.searchBookAuthor,
            }}).then(res=>{
                this.bookInfo = res.data.data
                console.log(res.data.data)
            })
        },
        handleAvatarSuccess(res, file){
            this.oneBookInfo.imgUrl = file.name;
            this.$forceUpdate()
        },
    }
}
</script>

<style>
.bookImage-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}
.bookImage-uploader .el-upload:hover {
    border-color: #409EFF;
    
}
.bookImage-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}
.bookImage {
    width: 178px;
    height: 178px;
    display: block;
}
</style>