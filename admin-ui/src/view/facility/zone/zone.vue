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
          title: '防区编号',
          key: 'number',
          tooltip: true,
          align: 'center'
        },
        {
          title: '防区名称',
          key: 'name',
          tooltip: true,
          align: 'center'
        },
        {
          title: '防区类型',
          tooltip: true,
          align: 'center',
          render: (h, params) => {
            if (!params.row.zoneType) return
            return h(
              'Tag',
              {
                props: {
                  color: 'success'
                }
              }, this.zoneTypeName[params.row.zoneType * 1 - 1]
            )
          }
        },
        {
          title: '报警方式',
          tooltip: true,
          align: 'center',
          render: (h, params) => {
            if (!params.row.armType) return
            return h(
              'Tag',
              {
                props: {
                  color: params.row.armType * 1 ? 'error' : 'success'
                }
              }, this.armTypeName[params.row.armType * 1]
            )
          }
        },
        {
          title: '布防报警状态',
          tooltip: true,
          align: 'center',
          render: (h, params) => {
            if (!params.row.homeType) return
            return h(
              'Tag',
              {
                props: {
                  color: params.row.homeType * 1 ? 'error' : 'success'
                }
              }, this.armTypeName[params.row.homeType * 1]
            )
          }
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
      totalCount: 0,
      zoneTypeName: ['普通防区', '延时报警防区', '24小时防区', '关闭防区'], // 1
      armTypeName: ['无声报警', '有声报警'], // 0
      homeType: ['不报警', '报警'] // 0
    }
  },
  methods: {
    ...mapActions(['getZoneList']),
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
      this.getZoneList(this.searchParam).then(res => {
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
