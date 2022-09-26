using Autofac;
using Carer.Repository.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Carer.Repository
{
    public class RepositoryDIModule : Module
    {
        protected override void Load(ContainerBuilder builder)
        {
            builder.RegisterType<UserRepository>().As<IUserRepository>();
            builder.RegisterType<PetRepository>().As<IPetRepository>();
            builder.RegisterType<AdRepository>().As<IAdRepository>();
            builder.RegisterType<AppliedUserRepository>().As<IAppliedUserRepository>();
        }
    }
}
