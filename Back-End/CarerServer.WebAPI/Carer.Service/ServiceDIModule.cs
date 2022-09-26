using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Autofac;
using Carer.Service.Common;

namespace Carer.Service
{
    public class ServiceDIModule : Module
    {
        protected override void Load(ContainerBuilder builder)
        {
            builder.RegisterType<UserService>().As<IUserService>();
            builder.RegisterType<LoginService>().As<ILoginService>();
            builder.RegisterType<PetService>().As<IPetService>();
            builder.RegisterType<AdService>().As<IAdService>();
            builder.RegisterType<AppliedUserService>().As<IAppliedUserService>();
        }
    }
}
