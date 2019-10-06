<template>
    <div>
      <Card class="card-search">
        <Form :model="searchParam" :label-width="80">
          <Row>
            <Col span="21">
              <FormItem label="名称">
                <Input v-model="searchParam.name" style="width:200px" placeholder="名称" clearable></Input>
              </FormItem>
            </Col>
            <Col span="3" class=" card-search-search-btn-box" :label-width="10">
              <Button type="primary" @click="search">搜索</Button>
            </Col>
          </Row>
        </Form>
      </Card>
      <Card class="card-content">
        <div class="card-content-table">
          <Table border
            ref="selection"
            :loading="loading"
            :columns="colData"
            :data="listData">
          </Table>
        </div>
        <div class="card-content-pages">
          <Page :total="totalCount"
            show-elevator
            show-total
            :page-size="searchParam.limit"
            @on-change='change'
            @on-page-size-change='change'>
          </Page>
        </div>
      </Card>
    </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      searchParam: {
        page: 1,
        limit: 10
      },
      loading: true,
      colData: [
        {
          type: 'selection',
          width: 60,
          align: 'center'
        },
        {
          title: '配件编号',
          key: 'number',
          tooltip: true,
          align: 'center'
        },
        {
          title: '配件名称',
          key: 'name',
          tooltip: true,
          align: 'center'
        },
        {
          title: '设备型号',
          key: 'devType',
          tooltip: true,
          align: 'center'
        },
        {
          title: '防区编号',
          key: 'zoneNumber',
          tooltip: true,
          align: 'center'
        },
        {
          title: '创建时间',
          key: 'createTime',
          tooltip: true,
          align: 'center'
        },
        {
          title: '更新时间',
          key: 'updateTime',
          tooltip: true,
          align: 'center'
        }
      ],
      listData: [],
      totalCount: 0
    }
  },
  methods: {
    ...mapActions(['getSensorList']),
    search () {
      this.searchParam.page = 1
      this.initData()
    },
    change (value) {
      this.searchParam.page = value
      this.initData()
    },
    initData () {
      this.loading = true
      this.getSensorList(this.searchParam).then(res => {
        this.loading = false
        this.listData = res.data.page.list
        this.totalCount = res.data.page.totalCount
      }).catch(err => {
        this.loading = false
        this.$Notice.error({
          title: '错误',
          desc: err.msg
        })
      })
    }
  },
  components: {
  },
  mounted () {
    this.initData()
  }
}
</script>
<style lang="less">
  @import "~@/style/style.less";
</style>
