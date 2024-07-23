<template>
  
    <div>


        <div class="num">
            <div class="rules-container">  
        <h1>图书借阅规则</h1>  
        <ul>  
            <li>规则一：本图书馆采用线上+押金模式借书。</li>  
            
            <li>规则二：请妥善保管图书，如有损坏或遗失不还押金。</li>  
            <li>规则三：请勿在图书上涂写、划线或撕页。</li>  
            <li>规则四：请按时归还图书，以便其他读者借阅。</li>  
            <li>规则五：如有问题请联系管理员邮箱：<br/>20225278@s.hlju.edu.cn</li> 

        </ul>  
    </div>  
            <div class="num2">
            <br/>

           
              <br/>
              
              
            </div>
            <div class="num3"></div>
            
        </div>
        <el-row type="flex" justify="space-around">
            <el-col :span="7">
                <div class="colBack">
                    <el-card class="newCard" v-for="(book, index) in newPcBooks" :key="index">
                        <el-image class="newImg" @click="toInfo(book)" :src="'https://www.xiaoqw.online/smallFrog-bookstore/img/' + book.img"></el-image>
                    </el-card>
                </div>
            </el-col>
            <el-col :span="7">
                <div class="colBack">
                    <el-card class="newCard" v-for="(book, index) in newEnBooks" :key="index">
                        <el-image class="newImg" @click="toInfo(book)" :src="'https://www.xiaoqw.online/smallFrog-bookstore/img/' + book.img"></el-image>
                    </el-card>
                </div>
            </el-col>
            <el-col :span="7">
                <div class="colBack">
                    <el-card class="newCard" v-for="(book, index) in newOtherBooks" :key="index">
                        <el-image class="newImg" @click="toInfo(book)" :src="'https://www.xiaoqw.online/smallFrog-bookstore/img/' + book.img"></el-image>
                    </el-card>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        data() {
            return {
                newPcBooks: [],
                newEnBooks: [],
                newOtherBooks: [],
            };
        },
        created() {
            var address = "https://www.xiaoqw.online/smallFrog-bookstore/server/newBooks.php";

            axios.post(address, 1).then(res => {
                    this.newPcBooks = res.data; //获取数据
                    console.log("success");
                    console.log(this.newPcBooks);
                }),
                axios.post(address, 2).then(res => {
                    this.newEnBooks = res.data; //获取数据
                    console.log("success");
                    console.log(this.newEnBooks);
                }),
                axios.post(address, 3).then(res => {
                    this.newOtherBooks = res.data; //获取数据
                    console.log("success");
                    console.log(this.newOtherBooks);
                })
        },
        methods: {
            toInfo(e) {
                this.$router.push({
                    path: "/bookInfo",
                    query: {
                        ID: e.ID
                    }
                });
            },
        },
    };
</script>

<style>

@font-face{
    font-family: "a";
    src:url(..\..\..\static\ziti\DuanNingMaoBiXingShuWanZhengBan-2.ttf);

}

.rules-container {  
    max-width: 800px;  
    margin: 0 auto;  
    font-family: a;
    padding: 20px;  
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);  
}  
  
.rules-container h1 {  
    text-align: center;  
    color: #333;  
    font-size: 24px;  
    margin-bottom: 20px;  
}  
  
.rules-container ul {  
    list-style-type: none;  
    padding: 0;  
}  
  
.rules-container li {  
    margin-bottom: 10px;  
    font-size: 26px;  
    line-height: 2;  
}  
  
.rules-container li::before {  
    content: "• ";  
    color: #666;  
    margin-right: 5px;  
}
    .colBack {
        padding: 25px;
        background-color: #f8f9fb;
    }

    .newCard {
        margin-top: 20px;
    }

    .newImg {
        width: 80px;
    }
    .num{
        margin: 0 auto;
        width: 700px;
        height: 600px;
        border: #3b5c9e 7px solid;
        border-radius: 20%;
        
    }
    .num2{
        margin: 0 auto;
        width: 100px;
        

    }
    .num3{
        margin: 0 auto;
        width: 400px;
        margin-top: 200px;
    }
</style>