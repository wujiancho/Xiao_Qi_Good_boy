package com.lx.xqgg.face_ui.home;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.face_ui.home.adapter.TopListAdapter;
import com.lx.xqgg.face_ui.home.bean.TopListBean;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class TopListActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<TopListBean> list = new ArrayList<>();
    private TopListAdapter topListAdapter;

    /**
     * 1 软件开发  2 法律咨询 3 财务策划
     */
    private int type = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_top_list;
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", -1);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        topListAdapter = new TopListAdapter(list);
        recyclerView.setAdapter(topListAdapter);
        topListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, FaceProductDetailActivity.class);
                intent.putExtra("data", list.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        switch (type) {
            case 0:
                tvTitle.setText("软件开发");
                list.add(new TopListBean("《APP开发》", R.drawable.img_appkf, "在不断的创新和实中总结出可持续和可信赖的设计流程,坚持与用户一起思考,用设计的方法发现问题、解决问题、翰出设计方案,并实现客户产品和企业价值的提升"
                        , "一、首先，制作一款APP，必须要有相关的idea（主意），也就是说，第一步是APP的idea（主意）形成。\n" +
                        "二、其次，就是通过那些idea来进行APP的主要功能设计以及大概界面构思和设计。\n" +
                        "三、app开发团队构架组成：产品构架  UI设计  安卓  IOS  后端  测试人员  这个比较基础具备app开发团队组成"
                        , "在移动互联网时代，很多人的需求都在网上得到满足。只要用户喜欢，在线就可以通过手机实现。吃、喝、玩、吃、穿、住、旅游，网络世界似乎更加丰富多彩。这也给许多企业带来了无限的商机"));
                list.add(new TopListBean("《网站开发》", R.drawable.img_wzkf, "网站开发是制作一些专业性强的网站，比如说动态网页。ASP、PHP、JSP网页。而且网站开发一般是原创，网站制作可以用别人的模板。网站开发字面意思比制作有更深层次的进步，它不仅仅是网站美工和内容，它可能涉及到域名注册查询、网站的一些功能的开发。对于较大的组织和企业，网站开发团队可以由数以百计的人（web开发者）组成。规模较小的企业可能只需要一个永久的或收缩的网站管理员，或相关的工作职位，如一个平面设计师和/或信息系统技术人员的二次分配。Web开发可能是一个部门，而不是域指定的部门之间的协作努力。"
                        , "\"网站开发是制作一些专业性强的网站，比如说动态网页。ASP、PHP、JSP网页。而且网站开发一般是原创，网站制作可以用别人的模板。网站开发字面意思比制作有更深层次的进步，它不仅仅是网站美工和内容，它可能涉及到域名注册查询、网站的一些功能的开发。对于较大的组织和企业，网站开发团队可以由数以百计的人（web开发者）组成。规模较小的企业可能只需要一个永久的或收缩的网站管理员，或相关的工作职位，如一个平面设计师和/或信息系统技术人员的二次分配。Web开发可能是一个部门，而不是域指定的部门之间的协作努力。"
                        , "如果按照你贴出的 php 的网址，那么自然是一个网页，根据 id 号不同而动态加载不同局部内容。而并不是多个网页。"));
                list.add(new TopListBean("《UI设计》", R.drawable.img_uisj, "UI设计（或称界面设计）是指对软件的人机交互、操作逻辑、界面美观的整体设计。UI设计分为实体UI和虚拟UI，互联网常用的UI设计是虚拟UI，UI即User Interface(用户界面)的简称。好的UI设计不仅是让软件变得有个性有品位，还要让软件的操作变得舒适简单、自由，充分体现软件的定位和特点。"
                        , "UI设计（或称界面设计）是指对软件的人机交互、操作逻辑、界面美观的整体设计。UI设计分为实体UI和虚拟UI，互联网常用的UI设计是虚拟UI，UI即User Interface(用户界面)的简称。好的UI设计不仅是让软件变得有个性有品位，还要让软件的操作变得舒适简单、自由，充分体现软件的定位和特点。"
                        , "越是简单的成品越是需要足够的积累。这也从侧面提醒我们UI设计这个专业需要学习的东西还有很多很多，因此想要以一个比较高的效率学到这么多的知识技能，接受培训变成最好的途径"));
                topListAdapter.notifyDataSetChanged();
                break;
            case 1:
                tvTitle.setText("法律咨询");
                list.add(new TopListBean("法律咨询", R.drawable.img_appkf,
                        "律师可以帮助当事人调查证据。当事人聘请律师以后，律师可以向有关单位和个人进行调查，获取有关对当事人有利的证据材料。律师还有权查阅案卷材料，全面了解案情。这样，就为当事人打好官司，切实维护自己的合法权益提供了较大的可能。"
                        , "1、要处分某项重要的权利时：您要改变您的财产分配、子女抚养权等重要事项，但又怕自己的做的改变在实际中不能起效时;\n" +
                        "2、某项权利受到侵害时：您吃了亏、遇到了不公，想要告某人或某公司或行政机关时;\n" +
                        "3、某项法律事务需要解决时：别人要找您打官司找您的麻烦"
                        , "1、与律师所在的律师事务所签订律师服务合同；\n" +
                        "2、根据律师服务合同的约定向律师事务所支付律师费，并由律师事务所出具税务发票；\n" +
                        "3、根据律师服务合同的约定，向律师出具办理法律事务必需的授权委托书。"));
                break;
            case 2:
                tvTitle.setText("财务策划");
                list.add(new TopListBean("《代理记账》", R.drawable.ic_dljz
                        , "委托、签订代理记帐合同，确定服务项目及费用。* 接票（时间：每月25日–次月3日）客户需提供相关资料如下:\n(1)各种以公司名字的费用发票以及交通、通讯发生的费用发票\n(2)当月开具的发票（本月1日---31日开具的所有发票的记账联及销售汇总表)一般纳税人企业需提供增值税清单（销项汇总清单、销项明细清单、进项发票认证清单）；\n(3)当月银行的所有回单及银行对账单，可从网银里打印或导出\n(4)工资表\n(5)社保的相关资料\n (6)现金收款填好收据或现金收款单，现金付款填好付款凭证或付款单，写明用途，盖上财务章，审核人签好字。"
                        , "1、代理形式\n\n" +
                        "2、工作特点\n\n" +
                        "3、人员特点\n\n" +
                        "4、财务税收和法规\n\n" +
                        "5、服务内容\n\n" +
                        "6、收费情况"
                        , "　对于财务记账，我们还可以根据数字出现的体现发现更深成的一面，比如群众的消费倾向，公司耗费的首先内容，并且记账可以增强公司的对资金的敏感度，可以让我们很有用的利用资金。一起还能有用培养我们个人养成好的关于消费的观念，让我们对于资金的使用愈加的合理，让消费趋势愈加的合理。"));
                list.add(new TopListBean("《公司变更》", R.drawable.ic_gsbg
                        , "本课程主要是老师分享30年管理经验总结，课程让您透彻管理的“逻辑”,掌握管理的“真知”,找到管理者成功的“路径”。60%以上全新内容，帮助广大企业家和管理者实现成功！"
                        , "一、变更地址\n\n" +
                        "二、变更法人\n\n" +
                        "三、变更股权\n\n" +
                        "四、变更名称\n\n" +
                        "五、变更注册资金\n\n" +
                        "六、变更经营范围\n"
                        , "只要合法的符合程序规定就没什么影响，公司变更名称的，变更名称就要把全套公司证件都做变更，并且和所有相关单位发布公告，应当自变更决议或者决定作出之日起30日内申请变更登记。公司变更名称要求公司注册时间在一年以上。公司变更名称时，所需要提供的材料:公司变更登记申请书。"));
                list.add(new TopListBean("《工商注册》", R.drawable.ic_gszc
                        , "市场中的通行证在相关的的国家地区申请注册商标的商品才能进驻该国家和地区各大型卖场、超市。做任何印刷广告宣传都需要出具商标注册证明文件。"
                        , "一、市场中的通行证\n\n" +
                        "二、消费者眼中的识别码\n\n" +
                        "三、商战中的旗帜\n\n" +
                        "四、品牌纠纷中的盾牌\n\n" +
                        "五、资产中的重头戏\n\n" +
                        "六、员工的勋章"
                        , "商标专用权质押贷款业务是以企业所有权质押,通过第三方评估其价值，银行采用灵活的抵、质押模式,为企业发放相对比例贷款。商标凝结了所标识产品、服务，以及该商品经营者、服务提供者的信誉，商标是服务信誉和与之相关的企业荣誉的最好标示。"));

                break;
        }
    }


    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }
}
