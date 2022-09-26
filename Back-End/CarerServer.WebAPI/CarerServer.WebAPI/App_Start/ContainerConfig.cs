using Autofac;
using Autofac.Integration.WebApi;
using AutoMapper.Contrib.Autofac.DependencyInjection;
using Carer.Repository;
using Carer.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Web;
using System.Web.Http;

namespace CarerServer.WebAPI.App_Start
{
    public class ContainerConfig
    {

        public static IContainer Container;

        public static void Initialize(HttpConfiguration config)
        {
            Initialize(config, RegisterServices(new ContainerBuilder()));
        }


        public static void Initialize(HttpConfiguration config, IContainer container)
        {
            config.DependencyResolver = new AutofacWebApiDependencyResolver(container);
        }

        private static IContainer RegisterServices(ContainerBuilder builder)
        {
            builder.RegisterApiControllers(Assembly.GetExecutingAssembly());
            builder.RegisterAssemblyModules(Assembly.GetExecutingAssembly());


            builder.RegisterModule(new RepositoryDIModule());
            builder.RegisterModule(new ServiceDIModule());

            builder.RegisterAutoMapper(typeof(AutoMapperProfile).Assembly);

            Container = builder.Build();

            return Container;
        }

    }
}
